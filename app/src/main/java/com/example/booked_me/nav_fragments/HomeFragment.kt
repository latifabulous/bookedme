package com.example.booked_me.nav_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.booked_me.DetailBookActivity
import com.example.booked_me.R

class HomeFragment : Fragment() {

    private lateinit var tv_book_title: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_book_title = view.findViewById(R.id.tv_book_title)

        tv_book_title.setOnClickListener {
            val detailIntent = Intent(activity, DetailBookActivity::class.java)
            startActivity(detailIntent)
        }
    }
}