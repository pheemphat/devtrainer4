package com.example.barber

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.barber.databinding.ActivityLoginBinding
import okhttp3.internal.userAgent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {


    private lateinit var loginBinding: ActivityLoginBinding
    var user = arrayListOf<Customer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        var intent = Intent (this,Home::class.java)

        loginBinding.btnLogin.setOnClickListener {
            if (loginBinding.edtUsername.text!!.isEmpty()  || loginBinding.edtPassword.text!!.isEmpty()) {
                Toast.makeText(applicationContext, "Please Input Your Username", Toast.LENGTH_LONG)
                    .show()
            }else{
                login()
            }
            loginBinding.goAdmin.setOnClickListener {
                var intent = Intent(this@Login,Admin::class.java)
                startActivity(intent)
            }
        }
    }

    fun login() {
        val loginRequest = LoginReq()
        loginRequest.setUser_email(loginBinding.edtUsername.text.toString())
        loginRequest.setUser_pass(loginBinding.edtPassword.text.toString())
        var loginResponseCall = CusApi.login().CusLogin(loginRequest)
        loginResponseCall.enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val sharedpreferences = getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
                    val editor = sharedpreferences.edit()
                    val id = response.body()?.Cus_ID.toString().toInt()
                    val name = response.body()?.Cus_Name.toString()
                    val username = response.body()?.Cus_Username.toString()
                    val password = response.body()?.Cus_Password.toString()
                    val age = response.body()?.Cus_Age.toString().toInt()
                    val gender = response.body()?.Cus_Gender.toString()
                    val tel= response.body()?.Cus_Tel.toString()
                    val image = response.body()?.Cus_Image.toString()

                    editor.putInt("IDKey", id)
                    editor.putString("nameKey", name)
                    editor.putString("usernameKey", username)
                    editor.putString("passwordKey", password)
                    editor.putInt("ageKey", age)
                    editor.putString("genderKey", gender)
                    editor.putString("telKey", tel)
                    editor.putString("imageKey", image)
                    editor.commit()



                    val intent = Intent(this@Login,Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Your Username or Password Is Incorrect", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

}