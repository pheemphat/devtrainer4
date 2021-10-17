package com.example.barber

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barber.databinding.ActivityAdminHomeBinding
import com.example.barber.databinding.ActivityAdminOwnerBinding

class AdminOwner : AppCompatActivity() {
    private  lateinit var bindingAdminOwner: ActivityAdminOwnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAdminOwner = ActivityAdminOwnerBinding.inflate(layoutInflater)
        setContentView(bindingAdminOwner.root)
        var sharedprefer =getSharedPreferences("Preference", Context.MODE_PRIVATE)
        bindingAdminOwner.Adminowner.text =sharedprefer.getString("usernameKey","")
    }
}