package com.example.booked_me.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.booked_me.R
import com.example.booked_me.presentation.onboarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val ivSplash : ImageView = findViewById(R.id.iv_splash)
        ivSplash.alpha = 0f
        ivSplash.animate().setDuration(1500).alpha(1f).withEndAction() {
            val i = Intent(this, OnBoardingActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

//        val handler = Handler()

//        handler.postDelayed({
//            val intent = Intent(this, OnBoardingActivity::class.java)
//            startActivity(intent)
//            finishAffinity()
//        }, 3000)
    }
}