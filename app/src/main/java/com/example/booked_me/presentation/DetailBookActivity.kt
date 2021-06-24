package com.example.booked_me.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ActivityDetailBookBinding
import com.example.booked_me.presentation.tab_fragments.adapters.ViewPagerAdapter
import com.example.booked_me.utils.Preference
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*

class DetailBookActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityDetailBookBinding
    private lateinit var backButton : Button
    private lateinit var database : DatabaseReference
    private lateinit var preference: Preference

    private lateinit var buku : Book

    private var isBookmark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener(this)

        preference = Preference(this)
        database = FirebaseDatabase.getInstance().getReference("user")

        buku = intent.getParcelableExtra<Book>("EXTRA_DATA") as Book
        Log.d("DetailBookActivity", buku.toString())

        supportActionBar?.elevation = 0f
        binding.viewPager.adapter = ViewPagerAdapter(this, buku)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(ViewPagerAdapter.TAB_TITLE[position])
        }.attach()

        showData()

        binding.btnBookmark.setOnClickListener {
            isBookmark = !isBookmark
            val book = Book()
            if (isBookmark){
                book.userInput = buku.userInput
                book.gambar = buku.gambar
                book.alamat = buku.alamat
                book.bahasa = buku.bahasa
                book.deskripsi = buku.deskripsi
                book.halaman = buku.halaman
                book.harga = buku.harga
                book.isBookmark = buku.isBookmark
                book.judul = buku.judul
                book.penulis = buku.penulis
                book.rating = buku.rating
                book.isBookmark = isBookmark

                database.child(preference.getValue("username").toString()).child("data_bookmark")
                    .child(buku.judul.toString()).setValue(book).addOnSuccessListener {
                        statusBookmark(isBookmark)
                        Toast.makeText(this, "Add to Bookmark", Toast.LENGTH_SHORT).show()
                    }

            } else {
                database.child(preference.getValue("username").toString()).child("data_bookmark")
                    .child(buku.judul.toString()).removeValue().addOnSuccessListener {
                        statusBookmark(isBookmark)
                        Toast.makeText(this, "Remove from Bookmark", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun statusBookmark(state : Boolean){
        if (state){
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_bookmark2_checked))
        } else {
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_bookmark2_selector))
        }
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

            database.child(preference.getValue("username").toString())
                .child("data_bookmark")
                .child(buku.judul.toString()).addValueEventListener( object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val dataBuku = snapshot.getValue(Book::class.java)

                        if (dataBuku?.isBookmark == true){
                            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailBookActivity, R.drawable.btn_bookmark2_checked))
                        } else {
                            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailBookActivity, R.drawable.btn_bookmark2_selector))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("DetailBookActivity", error.message)
                    }

                })
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