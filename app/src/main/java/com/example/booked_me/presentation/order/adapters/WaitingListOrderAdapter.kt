package com.example.booked_me.presentation.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.R
import com.example.booked_me.data.Order
import com.example.booked_me.databinding.ListDataOrderBinding
import com.squareup.picasso.Picasso

class WaitingListOrderAdapter(private val listData : List<Order>) :
    RecyclerView.Adapter<WaitingListOrderAdapter.WaitingListOrderViewHolder>(){
    inner class WaitingListOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ListDataOrderBinding.bind(itemView)

        fun bind(order: Order) {
            with(binding){
                tvBookOrderDate.text = order.total_pay
                tvBookPrice.text = order.harga
                tvBookStatus.text = order.status
                tvUserTotalPay.text = order.date
                tvBookTitle.text = order.judul_buku

                Picasso.get().load(order.gambar).into(imgBookCover)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitingListOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_data_order, parent, false)
        return WaitingListOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaitingListOrderViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}