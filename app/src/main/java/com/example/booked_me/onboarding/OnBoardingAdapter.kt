package com.example.booked_me.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.booked_me.R

class OnBoardingAdapter(val context: Context) : PagerAdapter() {

    companion object{
        private val LAYOUT_INT = intArrayOf(
            R.layout.onboarding_slide_one,
            R.layout.onboarding_slide_two,
            R.layout.onboarding_slide_three,
        )
    }

    override fun getCount(): Int = LAYOUT_INT.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(LAYOUT_INT[position], container, false)
        view.tag = position

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}