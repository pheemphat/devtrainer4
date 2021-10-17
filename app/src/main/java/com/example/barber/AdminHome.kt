package com.example.barber

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barber.databinding.ActivityAdminBinding
import com.example.barber.databinding.ActivityAdminHomeBinding
import com.example.barber.databinding.ActivityHomeBinding

class AdminHome : AppCompatActivity() {
    private  lateinit var bindingAdminHome: ActivityAdminHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        bindingAdminHome = ActivityAdminHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingAdminHome.root)
        var sharedprefer =getSharedPreferences("Preference", Context.MODE_PRIVATE)
        bindingAdminHome.userName.text =sharedprefer.getString("usernameKey","")
    }
}