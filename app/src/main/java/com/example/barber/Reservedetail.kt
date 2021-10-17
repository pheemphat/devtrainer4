package com.example.barber

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.barber.databinding.ActivityAdminOwnerBinding
import com.example.barber.databinding.ActivityHomeBinding
import com.example.barber.databinding.ActivityReservedetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reservedetail : AppCompatActivity() {
    private  lateinit var bindingdetail: ActivityReservedetailBinding
    var createclient = ReserveApi.create()
    var createclient2 = ServiceApi.create()
    var createclient3 = TimeApi.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservedetail)
        bindingdetail = ActivityReservedetailBinding.inflate(layoutInflater)
        setContentView(bindingdetail.root)

        showreserve()
        bindingdetail.backtohome.setOnClickListener {
            var intent = Intent(this@Reservedetail,Home::class.java)
            startActivity(intent)
        }
        bindingdetail.cancelreserve.setOnClickListener{
            var intent = Intent(this@Reservedetail,Reservcancel::class.java)
            startActivity(intent)
        }
    }

    fun showreserve() {
        var sharedpreferences =getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        var cusid =sharedpreferences.getInt("IDKey",0)
        Log.d("cusid",cusid.toString())
      createclient.getreserve(cusid)
       .enqueue(object : Callback<Reserve> {
            override fun onResponse(call: Call<Reserve>, response: Response<Reserve>) {
                Log.d("error1",response.body().toString())
                if (response.isSuccessful) {

                    val sharedreserve = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
                    val editor = sharedreserve.edit()
                    val id = response.body()?.RE_ID.toString()
                    val slip = response.body()?.RE_SlipPayment.toString()
                    val statusR = response.body()?.RE_StatusReserve.toString()
                    val statusP = response.body()?.RE_StatusPayment.toString()
                    val cusID = response.body()?.Cus_ID.toString()
                    val seID = response.body()?.SE_ID.toString()
                    val timeID = response.body()?.T_ID.toString()

                    editor.putInt("IDKey", 1)

                    editor.putString("slipKey", slip)
                    editor.putString("statusRKey", statusR)
                    editor.putString("statusPKey", statusP)
                    editor.putInt("cusIDKey", 2)
                    editor.putInt("seIDKey",3)
                    editor.putInt("tIDKey",4)
                    editor.commit()
                    //showreserve2()



                } else {
                    Toast.makeText(applicationContext, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Reserve>, t: Throwable) {
                Log.d("errorreserve",t.toString())
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showreserve2() {
        val sharedreserve = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
        var seID =sharedreserve.getInt("seIDKey",0)
      createclient2.getservice(seID)
        .enqueue(object : Callback<Service> {
            override fun onResponse(call: Call<Service>, response: Response<Service>) {
                if (response.isSuccessful) {
                    val sharedservice = getSharedPreferences("MyService", Context.MODE_PRIVATE)
                    val editor = sharedservice.edit()
                    val id = response.body()?.SE_ID.toString().toInt()
                    val sename = response.body()?.SE_Name.toString()
                    val seprice= response.body()?.SE_Price.toString().toInt()


                    editor.putInt("seIDKey",id)
                    editor.putString("senameKey",sename)
                    editor.putInt("sepriceKey", seprice)
                    editor.commit()
                    showreserve3()


                } else {
                    Toast.makeText(applicationContext, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Service>, t: Throwable) {
                Toast.makeText(applicationContext, "error show2", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun showreserve3() {

        val sharedreserve = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
        var timeID =sharedreserve.getInt("tIDKey",0)

        createclient3.gettime(timeID)
            .enqueue(object : Callback<Time> {
                override fun onResponse(call: Call<Time>, response: Response<Time>) {
                    if (response.isSuccessful) {
                        val sharedtime = getSharedPreferences("Mytime", Context.MODE_PRIVATE)
                        val editor = sharedtime.edit()
                        val tid= response.body()?.T_ID.toString().toInt()
                        val tdetail = response.body()?.T_Detail.toString()



                        editor.putInt("tIDKey",tid)
                        editor.putString("tDetail",tdetail )
                        editor.commit()
                        callreserve()



                    } else {
                        Toast.makeText(applicationContext, "Not Found", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Time>, t: Throwable) {
                    Toast.makeText(applicationContext, "error show2", Toast.LENGTH_LONG).show()
                }
            })
    }
    fun callreserve (){
        var sharedpreferences =getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        var cusname =sharedpreferences.getString("nameKey","")
        var custel =sharedpreferences.getString("telKey","")
        var cusgender =sharedpreferences.getString("genderKey","")
        var cusage =sharedpreferences.getInt("ageKey",0)

        val sharedreserve = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
        var slip =sharedpreferences.getString("slipKey","")

        val sharedservice = getSharedPreferences("MyReserve", Context.MODE_PRIVATE)
        var sename =sharedservice.getString("senameKey","")

        val sharedtime = getSharedPreferences("Mytime", Context.MODE_PRIVATE)
        var timeDetail =sharedtime.getString("tDetail","")
        bindingdetail.tvdetail.text = "ชื่อ : ${cusname} \n " +
                "เพศ : ${cusgender}      อายุ : ${cusage}\n" +
                "เบอร์โทร : ${custel} \n" +
                "เวลา : ${timeDetail} \n" +
                "หลักฐาน ${slip} \n " +
                "บริการ :  ${sename}"
    }
}