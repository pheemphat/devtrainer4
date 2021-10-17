package com.example.barber

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data  class Time (

    @Expose
    @SerializedName("T_ID") val T_ID: Int,

    @Expose
    @SerializedName("T_Detail") val T_Detail: String,

    ){

}