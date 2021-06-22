package com.example.booked_me.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.booked_me.R
import com.example.booked_me.databinding.ActivityDetailBookBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailBookActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityDetailBookBinding
    private lateinit var backButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_book)

        backButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener(this)

        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        binding.viewPager.adapter = com.example.booked_me.presentation.tab_fragments.adapters.ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(com.example.booked_me.presentation.tab_fragments.adapters.ViewPagerAdapter.TAB_TITLE[position])
        }.attach()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }

//    fun setUpTabs() {
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(SummaryFragment(), "Summary")
//        adapter.addFragment(OwnerFragment(), "Owner")
//        adapter.addFragment(ReviewFragment(), "Reviews")
//
//        val viewPager : ViewPager = findViewById(R.id.view_pager)
//        val tabLayout : TabLayout = findViewById(R.id.tab_layout)
//
//        viewPager.adapter = adapter
//
//        tabLayout.setupWithViewPager(viewPager)
//    }
}
