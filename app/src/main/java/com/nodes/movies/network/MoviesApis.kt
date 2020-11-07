package com.nodes.movies.network

import com.nodes.movies.BuildConfig.API_KEY
import com.nodes.movies.network.response.*
import com.nodes.movies.network.request.RateRequest
import retrofit2.Response
import retrofit2.http.*

interface MoviesApis {


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") pageNumber: Int = 1,
        @Query("query") searchQuery: String
    ): Response<MoviesResponse>

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
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}