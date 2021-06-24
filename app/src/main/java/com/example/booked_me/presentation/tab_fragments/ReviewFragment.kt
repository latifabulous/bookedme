package com.example.booked_me.presentation.tab_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booked_me.R
import com.example.booked_me.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    private lateinit var binding : FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rating = arguments?.getString("EXTRA_DATA")
        binding.tvBookAvgRating.text = rating
    }

}