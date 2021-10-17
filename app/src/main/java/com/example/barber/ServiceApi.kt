package com.example.barber

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceApi {

    @GET("reserve2/{SE_ID}")
    fun getservice(
        @Path("SE_ID")SE_ID:Int
    ): Call<Service>

    companion object{
        fun create(): ServiceApi {
            val cusClient:ServiceApi = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceApi::class.java)
            return cusClient
        }
    }
}