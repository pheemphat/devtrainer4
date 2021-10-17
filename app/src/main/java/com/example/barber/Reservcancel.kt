package com.example.barber

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barber.databinding.ActivityReservcancelBinding
import com.example.barber.databinding.ActivityReservedetailBinding

class Reservcancel : AppCompatActivity() {
    private  lateinit var bindingcancel: ActivityReservcancelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingcancel = ActivityReservcancelBinding.inflate(layoutInflater)
        setContentView( bindingcancel.root)
        bindingcancel.back.setOnClickListener {
            var intent = Intent(this@Reservcancel,Reservedetail::class.java)
            startActivity(intent)
        }

        val sharedreserve = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
        var date =sharedreserve.getString("date","")
        var timeB =sharedreserve.getString("timeBKey","")
        var timeA =sharedreserve.getString("timeAKey","")

        val sharedservice = getSharedPreferences("MyService", Context.MODE_PRIVATE)
        var sename =sharedservice.getString("timeAKey","")
        var seprice =sharedservice.getInt("timeAKey",0)

        bindingcancel.tvdetail.text = "บริการ : ${sename} \n" +
                "วันที่ : ${date} \n" +
                "เวลา : ${timeB} - ${timeA}"
        bindingcancel.tvprice.text = "ราคา : ${seprice}"
    }
}