package com.example.booked_me.presentation.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booked_me.R
import com.example.booked_me.data.Order
import com.example.booked_me.databinding.FragmentInProgressBinding
import com.example.booked_me.presentation.order.adapters.WaitingListOrderAdapter
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*

class InProgressFragment : Fragment() {

    private lateinit var binding : FragmentInProgressBinding
    private lateinit var preference: Preference
    private lateinit var database : DatabaseReference

    //atribut
    private var listData = ArrayList<Order>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preference(view.context)
        database = FirebaseDatabase.getInstance().getReference("user")

        binding.rvProgress.layoutManager = LinearLayoutManager(view.context)
        showData()
    }

    private fun showData() {
        database.child(preference.getValue("username").toString())
                .child("order_buku").addValueEventListener( object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        listData.clear()
                        for ( orders in snapshot.children){
                            val order = orders.getValue(Order::class.java)
                            listData.add(order!!)
                        }

                        binding.rvProgress.adapter = WaitingListOrderAdapter(listData)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("InProgressFragment", error.message)
                    }

                })
    }
}