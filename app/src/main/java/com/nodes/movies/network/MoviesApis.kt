package com.nodes.movies.network

import com.nodes.movies.BuildConfig.API_KEY
import com.nodes.movies.network.response.*
import com.tiendito.bmisrmovies.api.RateRequest
import retrofit2.Response
import retrofit2.http.*

interface MoviesApis {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<CreditsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MoviesResponse>

    @GET("authentication/guest_session/new")
    suspend fun generateGuestSession(
        @Query("api_key") apiKey: String = API_KEY): Response<GuestSessionResponse>

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}