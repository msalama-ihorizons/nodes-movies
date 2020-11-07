package com.nodes.movies.repository

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by Mohamed Salama on 11/6/2020.
 */
class SessionRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val GUEST_SESSION_KEY = "guestSessionKey"
    private val GUEST_SESSION_TIME_KEY = "guestSessionTimeKey"

    fun saveGuestSession(guestSession: String?) {
        sharedPreferences.edit().putString(GUEST_SESSION_KEY, guestSession).apply()
        sharedPreferences.edit().putLong(GUEST_SESSION_TIME_KEY, System.currentTimeMillis()).apply()
    }

    fun getGuestSession(): String? {
        return sharedPreferences.getString(GUEST_SESSION_KEY, null)
    }

    fun isExpired(): Boolean {
        val sessionTime = sharedPreferences.getLong(GUEST_SESSION_TIME_KEY, 0)
        val hoursDifference  = (System.currentTimeMillis() - sessionTime)/ (1000 * 60 * 60)
        if (hoursDifference >= 24)
            return true

        return  false
    }

}