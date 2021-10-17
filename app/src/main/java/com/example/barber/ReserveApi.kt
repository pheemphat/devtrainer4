package com.example.barber

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ReserveApi {


    @GET("reserve/{Cus_ID}")
    fun getreserve(
        @Path("Cus_ID")Cus_ID:Int
    ): Call<Reserve>



    @FormUrlEncoded
    @PUT("status/{user_id}")
    fun updateStatusOrder(
        @Path("user_id") user_id: Int,
        @Field("order_status") order_status: String
    ): Call<Reserve>

    companion object{
        fun create(): ReserveApi {
            val cusClient:ReserveApi = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReserveApi::class.java)
            return cusClient
        }
    }
}