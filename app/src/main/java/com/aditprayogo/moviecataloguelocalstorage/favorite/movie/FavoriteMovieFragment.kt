package com.aditprayogo.moviecataloguelocalstorage.favorite.movie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.moviecataloguelocalstorage.DetailMovieActivity

import com.aditprayogo.moviecataloguelocalstorage.R
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteMovieHelper
import com.aditprayogo.moviecataloguelocalstorage.helper.MappingHelper
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.fragment_favorite_movie.progressBar
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment() {
    private lateinit var adapter: FavoriteMovieAdapter
    private lateinit var favoriteMovieHelper: FavoriteMovieHelper

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_fav_mov.layoutManager = LinearLayoutManager(activity)
        rv_fav_mov.setHasFixedSize(true)
        adapter = FavoriteMovieAdapter()
        rv_fav_mov.adapter = adapter

        favoriteMovieHelper = FavoriteMovieHelper.getInstance(context!!)
        favoriteMovieHelper.open()

        adapter.setOnItemClickCallback(object : FavoriteMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteMovie) {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_FAV_MOVIE, data)
                startActivity(intent)
            }

        })

        if (savedInstanceState == null) {
            loadMoviesAsync()
        } else {

            val list = savedInstanceState.getParcelableArrayList<FavoriteMovie>(EXTRA_STATE)
            if (list != null) {
                adapter.listMovie = list
            }
            showLoading(false)
        }

    }



    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = favoriteMovieHelper.queryAll()
                MappingHelper.mapCursorToArrayListMovies(cursor)
            }
            showLoading(false)
            val movies = deferredMovies.await()
            if (movies.size > 0) {

                adapter.listMovie = movies
            }else {

                adapter.listMovie = ArrayList()
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovie)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteMovieHelper.close()
    }


}
