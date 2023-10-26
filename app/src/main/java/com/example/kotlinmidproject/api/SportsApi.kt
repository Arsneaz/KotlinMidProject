package com.example.kotlinmidproject.api

import com.example.kotlinmidproject.model.Data
import com.example.kotlinmidproject.model.PersonJson
import retrofit2.Call
import retrofit2.http.GET

interface SportsApi {
    @GET("users")
    suspend fun getData(): Call<PersonJson>
}