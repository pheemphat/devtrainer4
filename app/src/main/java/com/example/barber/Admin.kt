package com.example.barber

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.barber.databinding.ActivityAdminBinding
import com.example.barber.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Admin : AppCompatActivity() {
    private lateinit var Adminbinding: ActivityAdminBinding
    var user = arrayListOf<Customer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Adminbinding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(Adminbinding.root)
        var intent = Intent (this,Home::class.java)

        Adminbinding.btnLogin.setOnClickListener {
            if (Adminbinding.edtUsername.text!!.isEmpty()  || Adminbinding.edtPassword.text!!.isEmpty()) {
                Toast.makeText(applicationContext, "Please Input Your Username", Toast.LENGTH_LONG)
                    .show()
            }else{
                login()
            }
            Adminbinding.goLogin.setOnClickListener {
                var intent = Intent(this@Admin,Login::class.java)
                startActivity(intent)
            }
        }
    }

    fun login() {
        val loginRequest2= AdminReq()
        loginRequest2.setAdmin_username(Adminbinding.edtUsername.text.toString())
        loginRequest2.setAdmin_password(Adminbinding.edtPassword.text.toString())
        var loginResponseCall2 = AdminApi.Adminlog().AdminLogin(loginRequest2)
        loginResponseCall2.enqueue(object : Callback<AdminData> {
            override fun onResponse(call: Call<AdminData>, response: Response<AdminData>) {
                if (response.isSuccessful) {
                    val sharedprefer = getSharedPreferences("Preference", Context.MODE_PRIVATE)
                    val editor = sharedprefer.edit()
                    val id = response.body()?.SF_ID.toString().toInt()
                    val name = response.body()?.SF_Name.toString()
                    val tel= response.body()?.SF_Tel.toString()
                    val address = response.body()?.SF_Address.toString()
                    val username = response.body()?.SF_Username.toString()
                    val password = response.body()?.SF_Password.toString()
                    val gender = response.body()?.SF_Gender.toString()
                    val type = response.body()?.SF_Type.toString()
                    val age = response.body()?.SF_Age.toString().toInt()
                    val image = response.body()?.SF_Image.toString()

                    editor.putInt("IDKey", id)
                    editor.putString("nameKey", name)
                    editor.putString("telKey", tel)
                    editor.putString("addressKey", address)
                    editor.putString("usernameKey", username)
                    editor.putString("passwordKey", password)
                    editor.putString("genderKey", gender)
                    editor.putString("typeKey", type)
                    editor.putInt("ageKey", age)
                    editor.putString("imageKey", image)
                    editor.commit()

                    if (type=="staff"){
                        var intent= Intent(this@Admin, AdminHome::class.java)
                        startActivity(intent)
                    }else if(type=="owner") {
                        var intent = Intent(this@Admin, AdminOwner::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(applicationContext, "Your Username or Password Is Incorrect", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<AdminData>, t: Throwable) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }
        })
    }
}