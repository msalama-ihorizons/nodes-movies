package com.nodes.movies.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nodes.movies.network.response.Movie
import com.nodes.movies.repository.FavouritesRepository
import com.nodes.movies.repository.MoviesRepository
import com.nodes.movies.ui.moviedetails.MovieDetailsActivity.Companion.EXTRA_MOVIE_ID
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    private val favouritesRepository: FavouritesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val movieIDLiveData = savedStateHandle?.getLiveData<Int>(EXTRA_MOVIE_ID)

    private val ratingValueLiveData = MutableLiveData<Float>()

    val movieDetailsLiveData = movieIDLiveData?.switchMap { movieID ->
        moviesRepository.loadMovieDetails(movieID)
    }

    val movieCastLiveData = movieIDLiveData?.switchMap { movieID ->
        moviesRepository.loadMovieCast(movieID)
    }

    val similarMoviesLiveData = movieIDLiveData?.switchMap { movieID ->
        moviesRepository.loadSimilarMovies(movieID)
    }


    // Rate Movie LiveData
    val ratingMovieLiveData = ratingValueLiveData.switchMap { ratingValue ->
        moviesRepository.rateMovie(
            movieId = movieIDLiveData?.value,
            ratingValue = ratingValue
        )
    }

    fun rateMovie(rateValue: Float) {
        ratingValueLiveData.value = rateValue
    }

    val favouriteMovieLiveDate = movieIDLiveData?.switchMap { movieID ->
        favouritesRepository.isFavouriteMovie(movieID)
    }

    fun addToFavourites(isFavourite: Boolean, movie: Movie?) {
        viewModelScope.launch {
            favouritesRepository.addFavouriteMovie(isFavourite, movie)
        }
    }
}