package com.aditprayogo.moviecataloguelocalstorage.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditprayogo.moviecataloguelocalstorage.BuildConfig
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MovieViewModel : ViewModel() {

    companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    private val listMovies = MutableLiveData<ArrayList<Movie>>()

    fun setMovies() {
        val client = AsyncHttpClient()
        val items = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                //ketika sukses
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {

                        val movie = list.getJSONObject(i)
                        val movieItem = Movie(
                            movie.getInt("id"),
                            movie.getString("poster_path"),
                            movie.getString("title"),
                            movie.getString("overview"),
                            movie.getDouble("vote_average")
                        )
                        items.add(movieItem)
                    }

                    listMovies.postValue(items)

                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                //ketika gagal
                error.printStackTrace()
            }

        })
    }

    fun getMovies() : LiveData<ArrayList<Movie>> {
        return listMovies
    }

}