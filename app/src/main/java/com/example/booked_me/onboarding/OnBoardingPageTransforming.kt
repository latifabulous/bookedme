package com.example.booked_me.onboarding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.booked_me.R

class OnBoardingPageTransforming : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pagePosition = page.tag
        val pageWidth = page.width

        val pageWidthPosition = pageWidth * position
        val absPosition = Math.abs(position)

        val title = page.findViewById<TextView>(R.id.textView)
        title.alpha = 1.0f - absPosition

        val description = page.findViewById<TextView>(R.id.textView2)
        description.translationY = -pageWidthPosition/2f
        description.alpha = 1.0f - absPosition

        val image = page.findViewById<ImageView>(R.id.imageView4)
        if (image != null){
            image.alpha = 1.0f - absPosition
            image.translationX = -pageWidthPosition * 1.5f
        }


    }
}