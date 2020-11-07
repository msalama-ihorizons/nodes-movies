package com.nodes.ui.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nodes.movies.repository.MoviesRepository
import com.nodes.movies.ui.moviedetails.MovieDetailsActivity.Companion.EXTRA_MOVIE_ID
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Mohamed Salama on 9/8/2020.
 */
class MovieDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(MoviesRepository::class.java)
    private val savedStateHandle = Mockito.mock(SavedStateHandle::class.java)

    @Test
    fun testMovieDetails() {
        savedStateHandle.set(EXTRA_MOVIE_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadMovieDetails(332)
    }

    @Test
    fun testMovieCast() {
        savedStateHandle.set(EXTRA_MOVIE_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadMovieCast(332)
    }

    @Test
    fun testSimilarCast() {
        savedStateHandle.set(EXTRA_MOVIE_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadSimilarMovies(332)
    }
}