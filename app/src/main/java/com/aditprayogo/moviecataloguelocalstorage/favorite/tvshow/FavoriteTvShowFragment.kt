package com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.moviecataloguelocalstorage.DetailMovieActivity
import com.aditprayogo.moviecataloguelocalstorage.DetailTvShowActivity

import com.aditprayogo.moviecataloguelocalstorage.R
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteMovieHelper
import com.aditprayogo.moviecataloguelocalstorage.db.FavoriteTvShowHelper
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovie
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovieAdapter
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovieFragment
import com.aditprayogo.moviecataloguelocalstorage.helper.MappingHelper
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.fragment_favorite_movie.progressBar
import kotlinx.android.synthetic.main.fragment_favorite_tv_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment : Fragment() {

    private lateinit var adapter: FavoriteTvShowAdapter
    private lateinit var favoriteTvShowHelper: FavoriteTvShowHelper

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_fav_tv.layoutManager = LinearLayoutManager(activity)
        rv_fav_tv.setHasFixedSize(true)
        adapter = FavoriteTvShowAdapter()
        rv_fav_tv.adapter = adapter

        favoriteTvShowHelper = FavoriteTvShowHelper.getInstance(context!!)
        favoriteTvShowHelper.open()

        adapter.setOnItemClickCallback(object : FavoriteTvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteTvShow) {

                val intent = Intent(context, DetailTvShowActivity::class.java)
                intent.putExtra(DetailTvShowActivity.EXTRA_FAV_TV, data)
                startActivity(intent)
            }
        })

        if (savedInstanceState == null) {
            loadMoviesAsync()
        } else {

            val list = savedInstanceState.getParcelableArrayList<FavoriteTvShow>(
                FavoriteTvShowFragment.EXTRA_STATE
            )
            if (list != null) {
                adapter.listTvShow = list
            }
            showLoading(false)
        }

    }



    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = favoriteTvShowHelper.queryAll()
                MappingHelper.mapCursorToArrayListTvShow(cursor)
            }
            showLoading(false)
            val tvShow = deferredMovies.await()
            if (tvShow.size > 0) {

                adapter.listTvShow = tvShow
            }else {

                adapter.listTvShow = ArrayList()
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
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listTvShow)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteTvShowHelper.close()
    }


}
