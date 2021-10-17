package com.example.barber

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.barber.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private  lateinit var bindingHome: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bindingHome = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingHome.root)
        var sharedpreferences =getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        var customer =sharedpreferences.getString("usernameKey","")
        bindingHome.userName.text ="ยินดีต้อนรับ  ${customer}"

        bindingHome.showreserve.setOnClickListener {
            val intent = Intent(this@Home, Reservedetail::class.java)
            startActivity(intent)
        }
        bindingHome.reservebtn.setOnClickListener {
            val intent = Intent(this@Home, SelectService::class.java)
            startActivity(intent)
        }

    }


}