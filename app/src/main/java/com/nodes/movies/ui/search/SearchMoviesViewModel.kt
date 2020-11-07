package com.nodes.movies.ui.search

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nodes.movies.network.response.Movie
import com.nodes.movies.repository.MoviesRepository

class SearchMoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchKeywordLiveData = MutableLiveData<SearchLoad>()
    private var searchMoviesPage = 1


    private val allResultsList = mutableListOf<Movie>()
    val allResultsLiveData = MediatorLiveData<MutableList<Movie>>()

    var searchMoviesListLiveData = searchKeywordLiveData.switchMap { searchKeyWord ->
        moviesRepository.searchMovies(
            searchKeyWord.searchKeyword,
            searchKeyWord.pageNumber
        )
    }


    init {
        searchMovies("spider man")
        allResultsLiveData.addSource(searchMoviesListLiveData) {
            val newResult = it.data
            if (newResult != null) {
                allResultsList.addAll(newResult)
                allResultsLiveData.value = allResultsList
            }
        }
    }


    fun searchMovies(searchKeyword: String) {
        allResultsList.clear()
        searchMoviesPage = 1;
        searchKeywordLiveData.value = SearchLoad(searchKeyword, searchMoviesPage)
    }

    fun loadNextPage() {
        searchKeywordLiveData.value?.let {
            if (it.searchKeyword.isNotBlank()) {
                searchKeywordLiveData.value = SearchLoad(it.searchKeyword, searchMoviesPage++)
            }
        }
    }

    fun sortByRating() {
        allResultsLiveData.value = allResultsList.sortedBy { it.voteAverage }.toMutableList()
    }

    data class SearchLoad(val searchKeyword: String, val pageNumber: Int = 1)

}