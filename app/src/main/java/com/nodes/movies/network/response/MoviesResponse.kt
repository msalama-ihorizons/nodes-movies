package com.nodes.movies.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(@SerializedName("results") val movies: List<Movie>)

data class Genre(val id: Int, val name: String)

@Entity(tableName = "movies_table")
data class Movie(
    @PrimaryKey val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: List<Genre>
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Movie

        if (id != other.id) {
            return false
        }
        if (title != other.title) {
            return false
        }
        if (releaseDate != other.releaseDate) {
            return false
        }
        if (voteAverage != other.voteAverage) {
            return false
        }

        return true
    }
}