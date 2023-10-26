package com.example.kotlinmidproject.api

import com.example.kotlinmidproject.util.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SportsApi by lazy {
        retrofit.create(SportsApi::class.java)
    }

}