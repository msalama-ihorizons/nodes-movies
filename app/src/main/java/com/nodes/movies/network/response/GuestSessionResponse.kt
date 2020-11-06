package com.nodes.movies.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 11/6/2020.
 */

data class GuestSessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id")val guestSessionId: String
)