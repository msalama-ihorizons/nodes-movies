package com.nodes.movies.ui.favorites

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nodes.movies.repository.FavouritesRepository
import com.nodes.movies.repository.MoviesRepository

/**
 * Created by Mohamed Salama on 11/7/2020.
 */
class FavouritesMoviesViewModel @ViewModelInject constructor(
    private val favouritesRepository: FavouritesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val favMoviesListLiveData = favouritesRepository.loadFavouriteMovies()

}