package com.aditprayogo.moviecataloguelocalstorage.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class FavoriteTvShowHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.FavoriteTvShowColumns.TABLE_NAME
        private lateinit var databaseTvShowHelper: DatabaseTvShowHelper
        private var INSTANCE: FavoriteTvShowHelper? = null

        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context) : FavoriteTvShowHelper {

            if (INSTANCE == null) {
                INSTANCE = FavoriteTvShowHelper(context)
            }
            return INSTANCE as FavoriteTvShowHelper
        }
    }

    init {
        databaseTvShowHelper = DatabaseTvShowHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseTvShowHelper.writableDatabase
    }

    fun close() {
        databaseTvShowHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${DatabaseContract.FavoriteMovieColumns.ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?) : Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String) : Int {
        return database.delete(DatabaseContract.FavoriteTvShowColumns.TABLE_NAME,
            "${DatabaseContract.FavoriteTvShowColumns.ID} = '$id'", null
        )
    }
}