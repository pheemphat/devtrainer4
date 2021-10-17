package com.example.barber

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Blob

data class Customer (

    @Expose
    @SerializedName("Cus_ID") val Cus_ID: Int,

    @Expose
    @SerializedName("Cus_Name") val Cus_Name: String,

    @Expose
    @SerializedName("Cus_Username") val Cus_Username: String,

    @Expose
    @SerializedName("Cus_Password") val Cus_Password: String,

    @Expose
    @SerializedName("Cus_Age") val Cus_Age: Int,

    @Expose
    @SerializedName("Cus_Gender") val Cus_Gender: String,

    @Expose
    @SerializedName("Cus_Tel") val Cus_Tel: String,

    @Expose
    @SerializedName("Cus_Image") val Cus_Image: String,

    ) {
}