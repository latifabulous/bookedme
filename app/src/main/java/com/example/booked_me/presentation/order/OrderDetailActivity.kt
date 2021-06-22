package com.example.booked_me.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.booked_me.R

class OrderDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }
}