package com.example.booked_me.presentation.feeds

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import com.example.booked_me.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MyFeedsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnFloatAdd : com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_feeds)

        btnFloatAdd = findViewById(R.id.btn_float_add)

        btnFloatAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_float_add -> {
                val feedIntent = Intent(this@MyFeedsActivity, AddBookActivity::class.java)
                startActivity(feedIntent)
            }
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        btnFloatAdd.setOnClickListener {
//            val addBookIntent = Intent(activity, AddBookActivity::class.java)
//            startActivity(addBookIntent)
//        }
//
//    }
}