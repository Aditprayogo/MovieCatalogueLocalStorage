package com.aditprayogo.moviecataloguelocalstorage.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditprayogo.moviecataloguelocalstorage.movie.MovieViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class TvShowViewModel : ViewModel() {

    private val listTvShow = MutableLiveData<ArrayList<TvShow>>()

    fun setTvShow() {
        val client = AsyncHttpClient()
        val items = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=" + MovieViewModel.API_KEY + "&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                //ketika sukses
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val tvShow = list.getJSONObject(i)

                        val tvShowItem = TvShow(
                            tvShow.getInt("id"),
                            tvShow.getString("poster_path"),
                            tvShow.getString("name"),
                            tvShow.getString("overview"),
                            tvShow.getDouble("vote_average")
                        )

                        items.add(tvShowItem)
                    }

                    listTvShow.postValue(items)

                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                //ketika gagal
                error.printStackTrace()
            }

        })
    }

    fun getTvShow() : LiveData<ArrayList<TvShow>> {
        return listTvShow
    }

}