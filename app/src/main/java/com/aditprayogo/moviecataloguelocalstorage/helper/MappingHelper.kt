package com.aditprayogo.moviecataloguelocalstorage.helper

import android.database.Cursor
import com.aditprayogo.moviecataloguelocalstorage.db.DatabaseContract
import com.aditprayogo.moviecataloguelocalstorage.favorite.movie.FavoriteMovie
import com.aditprayogo.moviecataloguelocalstorage.favorite.tvshow.FavoriteTvShow

object MappingHelper {
    fun mapCursorToArrayListMovies(moviesCursor: Cursor) : ArrayList<FavoriteMovie> {

        val movieList = ArrayList<FavoriteMovie>()

        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovieColumns.ID))
            val photo = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovieColumns.POSTER_PATH))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovieColumns.TITLE))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovieColumns.OVERVIEW))
            val rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteMovieColumns.RATING))

            movieList.add(
                FavoriteMovie(id, photo, title, overview, rating)
            )

        }
        return movieList
    }

    fun mapCursorToArrayListTvShow(moviesCursor: Cursor) : ArrayList<FavoriteTvShow> {

        val movieList = ArrayList<FavoriteTvShow>()

        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.ID))
            val photo = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.POSTER_PATH))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.TITLE))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.OVERVIEW))
            val rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.RATING))

            movieList.add(
                FavoriteTvShow(id, photo, title, overview, rating)
            )

        }
        return movieList
    }

}