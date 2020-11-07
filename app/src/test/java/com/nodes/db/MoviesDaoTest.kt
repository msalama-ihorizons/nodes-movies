package com.nodes.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nodes.movies.db.MoviesDatabase
import com.nodes.movies.network.response.Movie
import com.nodes.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesDatabase: MoviesDatabase

    @Before
    fun initDB() {

        moviesDatabase =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java)
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        moviesDatabase.close()
    }

    @Test
   fun insertMovieTest() {
       val movie  = createMovie(550)
       runBlocking {
           moviesDatabase.moviesDao().insert(movie)
           val movies =  moviesDatabase.moviesDao().loadMovies()
            assert(movies.getOrAwaitValue().isNotEmpty())
       }
   }

    @Test
    fun loadMovieByIdTest() {
        val movieList  = listOf(createMovie(550), createMovie(432), createMovie(443))
        runBlocking {
            moviesDatabase.moviesDao().insertMovies(movieList)
            val movies =  moviesDatabase.moviesDao().loadMovieById(432)
            assertThat(movies.getOrAwaitValue()?.id, `is`(432))
        }
    }

    @Test
    fun deleteMovieTest() {
        val movieList  = listOf(createMovie(550), createMovie(432), createMovie(443))
        runBlocking {
            moviesDatabase.moviesDao().insertMovies(movieList)
            moviesDatabase.moviesDao().deleteMovie(550)

            val movies =  moviesDatabase.moviesDao().loadMovieById(550)
            assert(movies.getOrAwaitValue() == null)
        }
    }

    
    private fun createMovie(id: Int) : Movie {
        return Movie(
            id = id,
            title = "whiplash",
            releaseDate = "2014-10-20",
            voteAverage = 5.5,
            posterPath = "",
            backdropPath = "",
            overview = "",
            genres = emptyList()
        )
    }
}