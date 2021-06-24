package com.example.booked_me.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ActivityDetailBookBinding
import com.example.booked_me.presentation.tab_fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailBookActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityDetailBookBinding
    private lateinit var backButton : Button

    private lateinit var buku : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener(this)

        buku = intent.getParcelableExtra<Book>("EXTRA_DATA") as Book
        Log.d("DetailBookActivity", buku.toString())

        supportActionBar?.elevation = 0f
        binding.viewPager.adapter = ViewPagerAdapter(this, buku)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(ViewPagerAdapter.TAB_TITLE[position])
        }.attach()

        showData()
    }

    private fun showData() {
        with(binding){
            tvBookAuthor.text = buku.penulis
            tvBookLang.text = buku.bahasa
            tvBookNumpages.text = buku.halaman.toString()
            tvBookRating.text = buku.rating.toString()
            tvBookTitle.text = buku.judul

            Glide.with(this@DetailBookActivity)
                .load(buku.gambar)
                .into(imgBookCover)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }

}
