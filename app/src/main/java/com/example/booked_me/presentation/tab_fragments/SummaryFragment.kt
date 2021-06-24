package com.example.booked_me.presentation.tab_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booked_me.R
import com.example.booked_me.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var binding : FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buku = arguments?.getString("EXTRA_DATA")

        binding.tvBookSummary.text = buku
    }

}