package com.nodes.movies.di

import android.content.Context
import androidx.room.Room
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Mohamed Salama on 11/6/2020.
 */

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return  return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao {
        return db.moviesDao()
    }

}