package com.nodes.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.model.Resource
import com.nodes.movies.network.MoviesApis
import com.nodes.movies.network.response.Movie
import javax.inject.Inject

/**
 * Created by Mohamed Salama on 11/7/2020.
 */
class FavouritesRepository @Inject constructor(private val moviesDao: MoviesDao) {

    suspend fun addFavouriteMovie(isFavourite: Boolean, movie: Movie?) {

        if (isFavourite)
            moviesDao.insert(movie)
        else
            moviesDao.deleteMovie(movie?.id)
    }

    fun isFavouriteMovie(movieId: Int): LiveData<Boolean> {

        val resultLiveData = MediatorLiveData<Boolean>()

        resultLiveData.addSource(moviesDao.loadMovieById(movieId)) { movie ->
            movie?.let {
                resultLiveData.value = true
                return@addSource
            }
            resultLiveData.value = false

        }

        return resultLiveData
    }

    fun loadFavouriteMovies(): LiveData<Resource<List<Movie>>> {
        val resultLiveData = MediatorLiveData<Resource<List<Movie>>>()
        resultLiveData.value  = Resource.loading(null)

        resultLiveData.addSource(moviesDao.loadMovies()) {
                movies->
            resultLiveData.value = Resource.complete(null)

            resultLiveData.value = Resource.success(movies)
        }
        return resultLiveData
    }
}