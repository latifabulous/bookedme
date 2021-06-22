package com.example.booked_me.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.booked_me.R
import com.example.booked_me.databinding.ActivityOrderBinding
import com.example.booked_me.presentation.order.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrderActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBack : Button
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