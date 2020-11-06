package com.nodes.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nodes.movies.db.DataConverters
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.network.response.Movie

/**
 * Created by Mohamed Salama on 11/6/2020.
 */

@Database(entities = [Movie::class], version = 3, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}