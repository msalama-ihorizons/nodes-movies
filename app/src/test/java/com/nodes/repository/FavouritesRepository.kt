package com.nodes.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.db.MoviesDatabase
import com.nodes.movies.model.Resource
import com.nodes.movies.network.response.Movie
import com.nodes.movies.repository.FavouritesRepository
import com.nodes.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

/**
 * Created by Mohamed Salama on 11/7/2020.
 */

@RunWith(AndroidJUnit4::class)
class FavouritesRepository {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favouritesRepository: FavouritesRepository

    private val dao = Mockito.mock(MoviesDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(MoviesDatabase::class.java)
        Mockito.`when`(db.moviesDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        favouritesRepository = FavouritesRepository(dao)
    }



    @Test
    fun isFavourite_Return_True_Test() {
        val movie = MutableLiveData<Movie>()
        val observer = mock<Observer<Boolean>>()

        movie.postValue(createMovie(550))

        Mockito.`when`(dao.loadMovieById(550)).thenReturn(movie)

        favouritesRepository.isFavouriteMovie(550).observeForever(observer)

        Mockito.verify(observer).onChanged(true)

    }

    @Test
    fun isFavourite_Return_False_Test() {
        val movie = MutableLiveData<Movie>()
        val observer = mock<Observer<Boolean>>()

        movie.postValue(null)

        Mockito.`when`(dao.loadMovieById(550)).thenReturn(movie)

        favouritesRepository.isFavouriteMovie(550).observeForever(observer)

        Mockito.verify(observer).onChanged(false)

    }

    @Test
    fun loadAllFavouritesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()

        val movies = MutableLiveData<List<Movie>>()

        Mockito.`when`(dao.loadMovies()).thenReturn(movies)

        favouritesRepository.loadFavouriteMovies().observeForever(observer)

        Mockito.verify(observer).onChanged(Resource.loading(null))

        movies.postValue(emptyList())

        Mockito.verify(observer).onChanged(Resource.complete(null))
        Mockito.verify(observer).onChanged(Resource.success(emptyList()))

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