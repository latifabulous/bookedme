package com.example.booked_me.presentation.nav_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.booked_me.presentation.DetailBookActivity
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.FragmentHomeBinding
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var tv_book_title: TextView
    private lateinit var binding : FragmentHomeBinding
    private lateinit var firebase : DatabaseReference
    private lateinit var preferences : Preference

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
        tv_book_title = view.findViewById(R.id.tv_book_title)

        tv_book_title.setOnClickListener {
            val detailIntent = Intent(activity, DetailBookActivity::class.java)
            startActivity(detailIntent)
        }

        getData()
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