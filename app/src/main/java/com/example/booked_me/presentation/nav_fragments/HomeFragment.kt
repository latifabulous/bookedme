package com.example.booked_me.presentation.nav_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.booked_me.adapter.BookAdapter
import com.example.booked_me.presentation.DetailBookActivity
import com.example.booked_me.R
import com.example.booked_me.adapter.HorizontalBookAdapter
import com.example.booked_me.data.Book
import com.example.booked_me.data.User
import com.example.booked_me.databinding.FragmentHomeBinding
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var tv_book_title: TextView
    private lateinit var binding : FragmentHomeBinding
    private lateinit var firebase : DatabaseReference
    private lateinit var firebaseBuku : DatabaseReference
    private lateinit var preferences : Preference

    //atribut
    private var listBuku = ArrayList<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = Preference(requireContext())

        firebase = FirebaseDatabase.getInstance().getReference("user")
        firebaseBuku = FirebaseDatabase.getInstance().getReference("buku")

        getData()

        //readBuku
        binding.rvItem.layoutManager = LinearLayoutManager(view.context)
        binding.rvBuku.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false )

        firebaseBuku.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listBuku.clear()
                for (books in snapshot.children){
                    val buku = books.getValue(Book::class.java)
                    listBuku.add(buku!!)
                    Log.d("HomeFragment", buku.toString())
                }

                binding.rvItem.adapter = BookAdapter(listBuku)
                binding.rvBuku.adapter = HorizontalBookAdapter(listBuku)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", error.message)
            }

        })


    }

    private fun getData() {
        val username = preferences.getValue("username")
        firebase.child(username.toString()).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                binding.tvHiUname.setText("Hi, ${user?.username}!")
                Glide.with(requireContext())
                        .load(user?.photo)
                        .circleCrop()
                        .into(binding.ivAvatar)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("HomeFragment", error.message)
            }

        })
    }
}