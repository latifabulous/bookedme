package com.example.booked_me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.booked_me.databinding.ActivityDetailBookBinding
import com.example.booked_me.databinding.ActivityOrderBinding
import com.example.booked_me.tab_fragments.OwnerFragment
import com.example.booked_me.tab_fragments.ReviewFragment
import com.example.booked_me.tab_fragments.SummaryFragment
import com.example.booked_me.tab_fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailBookActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_book)

//        setUpTabs()

        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        binding.viewPager.adapter = com.example.booked_me.tab_fragments.adapters.ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(com.example.booked_me.tab_fragments.adapters.ViewPagerAdapter.TAB_TITLE[position])
        }.attach()
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
