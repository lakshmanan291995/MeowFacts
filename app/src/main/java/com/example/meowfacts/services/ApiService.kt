package com.example.meowfacts.services


import com.example.meowfacts.model.MeowFactResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("?count=1")
    fun getMeowFacts(
    ): Call<MeowFactResponse>
}