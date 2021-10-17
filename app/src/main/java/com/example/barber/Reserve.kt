package com.example.barber

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class Reserve (
    @Expose
    @SerializedName("RE_ID") val RE_ID: Int,

    @Expose
    @SerializedName("RE_Date") val RE_Date: Date,

    @Expose
    @SerializedName("RE_SlipPayment") val RE_SlipPayment: String,

    @Expose
    @SerializedName("RE_StatusReserve") val RE_StatusReserve: String,

    @Expose
    @SerializedName("RE_StatusPayment") val RE_StatusPayment: String,

    @Expose
    @SerializedName("Cus_ID") val Cus_ID: Int,

    @Expose
    @SerializedName("SE_ID") val SE_ID: Int,

    @Expose
    @SerializedName("T_ID") val T_ID: Int,

){}