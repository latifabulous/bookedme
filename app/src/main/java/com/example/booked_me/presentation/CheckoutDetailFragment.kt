package com.example.booked_me.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booked_me.data.Order
import com.example.booked_me.data.Transaksi
import com.example.booked_me.databinding.FragmentCheckoutDetailBinding
import com.example.booked_me.presentation.order.OrderActivity
import com.example.booked_me.presentation.order.adapters.OrderAdapter
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CheckoutDetailFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private var listOrder = ArrayList<Transaksi>()
    private lateinit var binding : FragmentCheckoutDetailBinding
    private lateinit var preference: Preference

    private var totalBayar : String? = null
    private var subBayar : String? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preference(view.context)
        databaseReference = FirebaseDatabase.getInstance().getReference("user")

        totalBayar = arguments?.getString("EXTRA_PRICE")
        subBayar = arguments?.getString("EXTRA_SUB_PRICE")

        binding.rvOrder.layoutManager = LinearLayoutManager(view.context)
        showData()

    }

    private fun showData() {
        databaseReference.child(preference.getValue("username").toString())
                .child("cart_user").addValueEventListener( object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        listOrder.clear()
                        for (orders in snapshot.children){
                            val order = orders.getValue(Transaksi::class.java)
                            listOrder.add(order!!)

                            val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY HH.MM")
                            val currentDate = simpleDateFormat.format(Date())

                            var orderData = Order()
                            orderData.gambar = order.gambar
                            orderData.harga = order.harga
                            orderData.judul_buku = order.judul_buku
                            orderData.date = currentDate.toString()
                            orderData.status = "Belum Bayar"

                            //show data
                            binding.tvUsername.text = order.user
                            binding.tvUserTlp.text = order.phone
                            binding.tvAddress.text = order.alamat_user
                            binding.tvAdminFee.text = "Rp. 2000"
                            binding.tvUserTotalPrice.text = "Rp. $subBayar"
                            binding.tvUserTotalPay.text = "Rp. $totalBayar"

                        }
                        binding.rvOrder.adapter = OrderAdapter(listOrder)

                        binding.btnOrder.setOnClickListener {

                            databaseReference.child(preference.getValue("username").toString())
                                    .child("cart_user").addValueEventListener( object : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (i in snapshot.children){

                                                val order = i.getValue(Order::class.java)

                                                var orderData = Order()

                                                val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY HH:MM:SS")
                                                val currentDate = simpleDateFormat.format(Date())

                                                orderData.judul_buku = order?.judul_buku
                                                orderData.gambar = order?.gambar
                                                orderData.status = "Belum Bayar"
                                                orderData.harga = order?.harga
                                                orderData.date = currentDate.toString()
                                                orderData.total_pay = totalBayar

                                                databaseReference.child(preference.getValue("username").toString())
                                                        .child("order_buku").child(order?.judul_buku.toString()).setValue(orderData)
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            Log.e("CheckoutDetailFragment", error.message)
                                        }


                                    })

                            databaseReference.child(preference.getValue("username").toString())
                                    .child("cart_user").removeValue()

                            activity?.finish()
                            val intent = Intent(view?.context, OrderActivity::class.java)
                            startActivity(intent)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("CheckoutDetailFragment", error.message)
                    }

                })

    }
}