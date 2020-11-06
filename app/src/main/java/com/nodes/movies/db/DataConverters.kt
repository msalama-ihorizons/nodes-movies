package com.nodes.movies.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nodes.movies.network.response.Genre

class DataConverters {

    @TypeConverter
    fun fromListToString(stringList: List<String?>?): String? {
        val type = object : TypeToken<List<String?>?>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun fromListToGenres(stringList: List<Genre?>?): String? {
        val type = object : TypeToken<List<Genre?>?>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun toStringList(str: String?): List<String>? {
        val type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson<List<String>>(str, type)
    }

    @TypeConverter
    fun toGenresList(str: String?): List<Genre>? {
        val type = object : TypeToken<List<Genre?>?>() {}.type
        return Gson().fromJson<List<Genre>>(str, type)
    }

}