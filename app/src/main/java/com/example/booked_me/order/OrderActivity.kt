package com.example.booked_me.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.booked_me.R
import com.example.booked_me.databinding.ActivityOrderBinding
import com.example.booked_me.order.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(ViewPagerAdapter.TAB_TITLE[position])
        }.attach()
    }
}