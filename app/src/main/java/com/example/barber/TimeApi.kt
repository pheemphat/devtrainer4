package com.example.barber

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TimeApi {
    @GET("reserve2/{T_ID}")
    fun gettime(
        @Path("T_ID")T_ID:Int
    ): Call<Time>

    companion object{
        fun create(): TimeApi {
            val cusClient:TimeApi= Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TimeApi::class.java)
            return cusClient
        }
    }
}