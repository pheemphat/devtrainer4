package com.example.barber

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CusApi {

    @POST("login")
    fun CusLogin(
        @Body loginReq: LoginReq
    ): Call<Customer>

    @GET("getsf")
    fun GetCus(): Call<List<Customer>>





    companion object{
        fun getRetrofit(): Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

            val cusClient = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")  // nodeJs 10.0.2.2:3000
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return cusClient
        }

        fun login(): CusApi {
            val CusService: CusApi = getRetrofit().create(CusApi::class.java)
            return CusService
        }
    }




}