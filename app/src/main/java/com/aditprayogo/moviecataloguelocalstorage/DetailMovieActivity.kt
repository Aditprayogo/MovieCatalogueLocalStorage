package com.aditprayogo.moviecataloguelocalstorage

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.aditprayogo.moviecataloguelocalstorage.db.DatabaseContract
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteMovieHelper
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovie
import com.aditprayogo.moviecataloguelocalstorage.movie.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private var pathPoster = "https://image.tmdb.org/t/p/w300"
    private var movie: Movie? = null
    private var favMovie: FavoriteMovie? = null
    private var position: Int = 0
    private var isFav = false
    private lateinit var favoriteMovieHelper: FavoriteMovieHelper


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_FAV_MOVIE = "extra_fav_movie"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val EXTRA_POSITION_MOVIE = "extra_position_movie"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        favoriteMovieHelper = FavoriteMovieHelper.getInstance(applicationContext)

        movie = intent?.getParcelableExtra(EXTRA_MOVIE)
        favMovie = intent?.getParcelableExtra(EXTRA_FAV_MOVIE)

        if (favMovie != null) {

            position = intent.getIntExtra(EXTRA_POSITION_MOVIE, 0)
            isFav = true
            showFavMovie(favMovie!!)

        } else {

            showMovie(movie!!)
            favMovie = FavoriteMovie()

        }
    }

    private fun showFavMovie(data: FavoriteMovie) {
        tv_title.text = data.title
        tv_rating.text = data.rating.toString()
        tv_overview.text = data.overview

        Glide.with(this)
            .load(pathPoster + data.photo)
            .into(iv_image)
    }

    private fun showMovie(data: Movie) {
        tv_title.text = data.title
        tv_rating.text = data.rating.toString()
        tv_overview.text = data.overview

        Glide.with(this)
            .load(pathPoster + data.photo)
            .into(iv_image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.fav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite -> {
                movie?.let { addToFavorite(it) }
                return true
            }
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return true
        }
    }

    private fun addToFavorite(movie: Movie) {
        favMovie?.id = movie.id
        favMovie?.title = movie.title
        favMovie?.rating = movie.rating
        favMovie?.overview = movie.overview
        favMovie?.photo = movie.photo

        val intent = Intent()
        intent.putExtra(EXTRA_FAV_MOVIE, favMovie)
        intent.putExtra(EXTRA_POSITION_MOVIE, position)

        val values = ContentValues()
        values.put(DatabaseContract.FavoriteMovieColumns.ID, movie.id)
        values.put(DatabaseContract.FavoriteMovieColumns.TITLE, movie.title)
        values.put(DatabaseContract.FavoriteMovieColumns.RATING, movie.rating)
        values.put(DatabaseContract.FavoriteMovieColumns.OVERVIEW, movie.overview)
        values.put(DatabaseContract.FavoriteMovieColumns.POSTER_PATH, movie.photo)

        favoriteMovieHelper.open()
        val result = favoriteMovieHelper.insert(values)

        if (result > 0) {
            favMovie?.id = result.toInt()
            setResult(RESULT_ADD, intent)
            Toast.makeText(this, getString(R.string.favorite_added), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.movie_favorite_already), Toast.LENGTH_SHORT).show()
        }

    }
}
