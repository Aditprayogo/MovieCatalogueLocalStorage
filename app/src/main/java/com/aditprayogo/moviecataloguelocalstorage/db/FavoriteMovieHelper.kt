package com.aditprayogo.moviecataloguelocalstorage.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.aditprayogo.moviecataloguelocalstorage.db.DatabaseContract.FavoriteMovieColumns.Companion.ID
import com.aditprayogo.moviecataloguelocalstorage.db.DatabaseContract.FavoriteMovieColumns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteMovieHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseMovieHelper: DatabaseMovieHelper
        private var INSTANCE: FavoriteMovieHelper? = null

        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context) : FavoriteMovieHelper {
            if (INSTANCE == null) {
                INSTANCE = FavoriteMovieHelper(context)
            }
            return INSTANCE as FavoriteMovieHelper
        }
    }

    init {
        databaseMovieHelper = DatabaseMovieHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseMovieHelper.writableDatabase
    }

    fun close() {
        databaseMovieHelper.close()

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
            "$ID = ?",
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
        return database.delete(TABLE_NAME, "$ID = '$id'", null)
    }


}