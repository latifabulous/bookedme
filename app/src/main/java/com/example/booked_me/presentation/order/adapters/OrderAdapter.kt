package com.example.booked_me.presentation.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.R
import com.example.booked_me.data.Transaksi
import com.example.booked_me.databinding.ListOrderBinding
import com.squareup.picasso.Picasso

class OrderAdapter(private val listOrder : List<Transaksi>)
    : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var binding = ListOrderBinding.bind(itemView)

        fun bind(transaksi: Transaksi){
            binding.tvBookAuthor.text = "transaksi.judul_buku"
            binding.tvBookPrice.text = transaksi.harga
            Picasso.get()
                .load(transaksi.gambar)
                .into(binding.imgBookCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cart, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }

    override fun getItemCount(): Int = listOrder.size
}