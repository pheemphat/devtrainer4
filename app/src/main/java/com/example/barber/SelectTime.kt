package com.example.barber

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class SelectTime : AppCompatActivity() {

    private lateinit var tvdatePicker : TextView
    private lateinit var btndatePicker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_time)

        tvdatePicker = findViewById(R.id.tvdate)
        btndatePicker = findViewById(R.id.datePicker)

        val cal = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(cal)
        }
        btndatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)
                , cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLable(cal: Calendar) {
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        tvdatePicker.setText(sdf.format(cal.time))

    }

    fun backService(view: View) {}
    fun btReserve(view: View) {}
}