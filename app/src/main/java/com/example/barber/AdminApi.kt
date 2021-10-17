package com.example.barber

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminApi {



    @POST("Adminlogin")
    fun AdminLogin(
        @Body adminReq: AdminReq
    ): Call<AdminData>

    @GET("getsf")
    fun GetCus(): Call<List<AdminData>>





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

        fun Adminlog(): AdminApi{
            val CusService: AdminApi = getRetrofit().create(AdminApi::class.java)
            return CusService
        }
    }

}