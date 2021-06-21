package com.example.booked_me.presentation.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.booked_me.presentation.MainActivity
import com.example.booked_me.R
import com.example.booked_me.databinding.ActivityOnBoardingBinding
import com.example.booked_me.presentation.login_register.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingBinding
    private lateinit var adapter : OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = OnBoardingAdapter(this)

        with(binding){

            viewpager.adapter = adapter
            viewpager.setPageTransformer(false, OnBoardingPageTransforming())
        }
    }

    fun nextPage(view : View){
        if (view.id == R.id.button){
            if (binding.viewpager.currentItem <= adapter.count - 1){
                binding.viewpager.setCurrentItem(binding.viewpager.currentItem + 1, false)
            }
//            else{
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
        }
    }

    fun toLoginPage(view : View){
        if (view.id == R.id.button){
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}