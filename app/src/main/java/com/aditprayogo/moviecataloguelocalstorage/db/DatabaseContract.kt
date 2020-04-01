package com.aditprayogo.moviecataloguelocalstorage.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class FavoriteMovieColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_movie"
            const val ID = "_id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val RATING = "rating"
            const val POSTER_PATH = "poster_path"
        }
    }

    internal class FavoriteTvShowColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_movie"
            const val ID = "_id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val RATING = "rating"
            const val POSTER_PATH = "poster_path"
        }
    }



}