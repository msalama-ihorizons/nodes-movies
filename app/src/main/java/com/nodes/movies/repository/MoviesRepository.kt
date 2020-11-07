package com.nodes.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.nodes.movies.db.MoviesDao
import com.nodes.movies.model.Resource
import com.nodes.movies.network.MoviesApis
import com.nodes.movies.network.response.Cast
import com.nodes.movies.network.response.Movie
import com.nodes.movies.network.response.RateResponse
import com.tiendito.bmisrmovies.api.*
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApis: MoviesApis,
    private val sessionRepository: SessionRepository
) {


    fun searchMovies(searchQuery: String, pageNumber: Int): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = moviesApis.searchMovies(
                    searchQuery = searchQuery,
                    pageNumber = pageNumber
                )

                emit(Resource.complete(null))

                if (result.isSuccessful) {
                    emit(Resource.success(result.body()?.movies))
                } else {
                    emit(Resource.error(result.message(), null))
                }

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }

        }
    }

    fun loadSimilarMovies(movieId: Int): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = moviesApis.getSimilarMovies(movieId = movieId)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.movies?.sortedBy { it.title }))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun loadMovieDetails(movieId: Int): LiveData<Resource<Movie>> {

        return liveData {

            emit(Resource.loading(null))

            try {
                val result = moviesApis.getMovieDetails(movieId = movieId)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun loadMovieCast(movieId: Int): LiveData<Resource<List<Cast>>> {

        return liveData {

            emit(Resource.loading(null))

            try {
                val result = moviesApis.getMovieCast(movieId = movieId)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.cast))
                else
                    emit(Resource.error(result.message(), null))
            }catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun rateMovie(movieId: Int?, ratingValue: Float): LiveData<Resource<RateResponse>> {
        return liveData {

            emit(Resource.loading(null))

            if (sessionRepository.isExpired()) {
                val sessionResult = moviesApis.generateGuestSession()
                if (sessionResult.isSuccessful)
                    sessionRepository.saveGuestSession(sessionResult.body()?.guestSessionId)
            }

            try {

                val result = moviesApis.rateMovie(
                    movieId = movieId ?: -1,
                    guestSessionId = sessionRepository.getGuestSession() ?: "",
                    rateRequest = RateRequest(ratingValue)
                )

                if (result.isSuccessful)
                    emit(Resource.success(result.body()))
                else
                    emit(Resource.error(result.message(), null))

                emit(Resource.complete(null))


            } catch (e: Exception) {
                emit(Resource.error(e.message, null))
                emit(Resource.complete(null))
            }

        }
    }

}