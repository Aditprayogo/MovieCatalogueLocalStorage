package com.aditprayogo.moviecataloguelocalstorage

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.aditprayogo.moviecataloguelocalstorage.db.DatabaseContract
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteTvShowHelper
import com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow.FavoriteTvShow
import com.aditprayogo.moviecataloguelocalstorage.tvshow.TvShow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : AppCompatActivity() {

    private var pathPoster = "https://image.tmdb.org/t/p/w300"
    private var tvShow: TvShow? = null
    private var favTv: FavoriteTvShow? = null
    private var position: Int = 0
    private var isFav = false
    private lateinit var favoriteTvShowHelper: FavoriteTvShowHelper


    companion object {
        const val EXTRA_TV = "extra_tv"
        const val EXTRA_FAV_TV = "extra_fav_tv"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val EXTRA_POSITION_TV = "extra_position_tv"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        favoriteTvShowHelper = FavoriteTvShowHelper.getInstance(applicationContext)

        tvShow = intent?.getParcelableExtra(EXTRA_TV)
        favTv = intent?.getParcelableExtra(EXTRA_FAV_TV)

        if (favTv != null) {

            position = intent.getIntExtra(EXTRA_POSITION_TV, 0)
            isFav = true
            showFavTv(favTv!!)

        } else {

            showTv(tvShow!!)
            favTv = FavoriteTvShow()

        }
    }

    private fun showFavTv(data: FavoriteTvShow) {
        tv_title.text = data.title
        tv_rating.text = data.rating.toString()
        tv_overview.text = data.overview

        Glide.with(this)
            .load(pathPoster + data.photo)
            .into(iv_image)
    }

    private fun showTv(data: TvShow) {
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
                tvShow?.let { addToFavorite(it) }
                return true
            }
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return true
        }
    }

    private fun addToFavorite(data: TvShow) {
        favTv?.id = data.id
        favTv?.title = data.title
        favTv?.rating = data.rating
        favTv?.overview = data.overview
        favTv?.photo = data.photo

        val intent = Intent()
        intent.putExtra(EXTRA_FAV_TV, favTv)
        intent.putExtra(EXTRA_POSITION_TV, position)

        val values = ContentValues()
        values.put(DatabaseContract.FavoriteMovieColumns.ID, data.id)
        values.put(DatabaseContract.FavoriteMovieColumns.TITLE, data.title)
        values.put(DatabaseContract.FavoriteMovieColumns.RATING, data.rating)
        values.put(DatabaseContract.FavoriteMovieColumns.OVERVIEW, data.overview)
        values.put(DatabaseContract.FavoriteMovieColumns.POSTER_PATH, data.photo)

        favoriteTvShowHelper.open()
        val result = favoriteTvShowHelper.insert(values)

        if (result > 0) {
            favTv?.id = result.toInt()
            setResult(RESULT_ADD, intent)
            Toast.makeText(this, getString(R.string.favorite_added), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.movie_favorite_already), Toast.LENGTH_SHORT).show()
        }

    }
}
