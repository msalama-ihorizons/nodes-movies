package com.nodes.movies.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 11/6/2020.
 */
data class RateResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val success: Boolean
)