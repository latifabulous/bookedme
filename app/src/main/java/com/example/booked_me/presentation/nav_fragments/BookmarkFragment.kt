package com.example.booked_me.presentation.nav_fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.data.Transaksi
import com.example.booked_me.data.User
import com.example.booked_me.presentation.nav_fragments.adapter.BookmarkAdapter
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class BookmarkFragment : Fragment(), View.OnClickListener {

    private lateinit var btnBack : Button
    private lateinit var rvBookmark : RecyclerView
    private lateinit var btnAddCart : Button

    private lateinit var database : DatabaseReference
    private lateinit var preference: Preference

    private lateinit var listBuku : ArrayList<Book>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preference(view.context)
        database = FirebaseDatabase.getInstance().getReference("user")

        rvBookmark = view.findViewById(R.id.rv_bookmark)
        btnBack = view.findViewById(R.id.btn_back)
        btnAddCart = view.findViewById(R.id.btn_add_cart)
        btnBack.setOnClickListener(this)
        btnAddCart.setOnClickListener(this)
        listBuku = ArrayList<Book>()

        getBookmark()

        rvBookmark.layoutManager = LinearLayoutManager(view.context)
        rvBookmark.setHasFixedSize(true)
    }

    private fun getBookmark() {
        database.child(preference.getValue("username").toString()).child("data_bookmark")
            .addValueEventListener( object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listBuku.clear()
                    for (books in snapshot.children){
                        val book = books.getValue(Book::class.java)
                        listBuku.add(book!!)
                    }

                    rvBookmark.adapter = BookmarkAdapter(listBuku)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BookmarkFragment", error.message)
                }

            })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_back -> {

            }
            R.id.btn_add_cart -> {
                database.child(preference.getValue("username").toString()).addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val user = snapshot.getValue(User::class.java)

                            database.child(preference.getValue("username").toString()).child("data_bookmark")
                                .addValueEventListener( object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (buku in snapshot.children){
                                            var book = buku.getValue(Book::class.java)

                                            val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY HH:MM:SS")
                                            val currentDate = simpleDateFormat.format(Date())

                                            val transaksi = Transaksi()
                                            transaksi.user = user?.username
                                            transaksi.address_user = user?.address
                                            transaksi.phone = user?.phone
                                            transaksi.price = book?.harga
                                            transaksi.judul_buku = book?.judul
                                            transaksi.date = currentDate.toString()
                                            transaksi.gambar = book?.gambar

                                            database.child(preference.getValue("username").toString())
                                                .child("cart_user").child(book?.judul.toString()).setValue(transaksi)
                                                .addOnSuccessListener {
                                                    Toast.makeText(view?.context, "Add to Cart to Success", Toast.LENGTH_SHORT).show()
                                                }


                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.e("BookmarkFragment", error.message)
                                    }

                                })

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("BookmarkFragment", error.message)
                        }

                    }
                )
            }
        }
    }
}