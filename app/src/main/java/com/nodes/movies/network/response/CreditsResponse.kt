package com.nodes.movies.network.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse (@SerializedName("cast") val cast: List<Cast>)

data class Cast(
    @SerializedName("character") val character: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Cast

        if (character != other.character) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (profilePath != other.profilePath) {
            return false
        }

        return true
    }
}
