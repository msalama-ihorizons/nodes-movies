package com.nodes.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.db.MoviesDatabase
import com.nodes.movies.model.Resource
import com.nodes.movies.network.MoviesApis
import com.nodes.movies.network.response.Movie
import com.nodes.movies.network.response.MoviesResponse
import com.nodes.movies.repository.MoviesRepository
import com.nodes.movies.repository.SessionRepository
import com.nodes.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

@RunWith(AndroidJUnit4::class)
class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesRepository: MoviesRepository

    private val sessionRepository = Mockito.mock(SessionRepository::class.java)
    private val service = Mockito.mock(MoviesApis::class.java)
    private val dao = Mockito.mock(MoviesDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(MoviesDatabase::class.java)
        Mockito.`when`(db.moviesDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        moviesRepository = MoviesRepository(service, sessionRepository)
    }


    @Test
    fun searchMoviesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()

        val moviesResponse = MoviesResponse(emptyList())

        val apiResponse = Response.success(moviesResponse)

        runBlocking {
            Mockito.`when`(service.searchMovies(searchQuery = "whiplash")).thenReturn(apiResponse)

            moviesRepository.searchMovies(searchQuery = "whiplash", pageNumber = 1).observeForever(observer)

            Mockito.verify(observer).onChanged(Resource.loading(null))
            Mockito.verify(observer).onChanged(Resource.complete(null))
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
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