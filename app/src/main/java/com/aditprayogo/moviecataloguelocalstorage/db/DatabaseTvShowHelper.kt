package com.aditprayogo.moviecataloguelocalstorage.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseTvShowHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoritetv"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_FAVORITE_MOVIE = "CREATE TABLE ${DatabaseContract.FavoriteTvShowColumns.TABLE_NAME}" +
                "(${DatabaseContract.FavoriteTvShowColumns.ID} INTEGER PRIMARY KEY," +
                "${DatabaseContract.FavoriteTvShowColumns.TITLE} TEXT NOT NULL," +
                "${DatabaseContract.FavoriteTvShowColumns.OVERVIEW} TEXT NOT NULL," +
                "${DatabaseContract.FavoriteTvShowColumns.RATING} DOUBLE NOT NULL," +
                "${DatabaseContract.FavoriteTvShowColumns.POSTER_PATH} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.FavoriteMovieColumns.TABLE_NAME}")
        onCreate(db)
    }


}