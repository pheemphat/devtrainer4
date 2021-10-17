package com.example.barber

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Time

data class Service (
    @Expose
    @SerializedName("SE_ID") val SE_ID :Int,

    @Expose
    @SerializedName("SE_Name") val SE_Name: String,

    @Expose
    @SerializedName("SE_Price") val SE_Price: Int


){}