package com.example.booked_me.presentation.nav_fragments.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.data.Transaksi
import com.example.booked_me.databinding.ListCartBinding
import com.squareup.picasso.Picasso

class CartAdapter(private val listCart : List<Transaksi>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListCartBinding.bind(itemView)

        fun bind(transaksi: Transaksi){
            with(binding){
                tvBookTitle.text = transaksi.title_book
                tvHarga.text = "Rp. ${transaksi.price}"

                Picasso.get()
                    .load(transaksi.gambar)
                    .into(imgBookCover)

                addSum.setOnClickListener {
                    var count = sumBook.text.toString().trim()
                    var numberBook = Integer.parseInt(count)
                    if (numberBook >= 9) return@setOnClickListener
                    numberBook++
                    sumBook.text = numberBook.toString()
                }

                minusSum.setOnClickListener {
                    var count = sumBook.text.toString().trim()
                    var numberBook = Integer.parseInt(count)
                    if (numberBook == 1) return@setOnClickListener
                    numberBook--
                    sumBook.text = numberBook.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(listCart[position])
    }

    override fun getItemCount(): Int {
        return listCart.size
    }
}