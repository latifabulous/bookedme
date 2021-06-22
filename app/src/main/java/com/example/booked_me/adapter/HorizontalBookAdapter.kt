package com.example.booked_me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ListBookBinding

class HorizontalBookAdapter(private var listBuku : List<Book>)
    : RecyclerView.Adapter<HorizontalBookAdapter.HorizontalBookViewHolder>() {
    class HorizontalBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListBookBinding.bind(itemView)
        fun bind(book: Book){
            with(binding){
                tvBookAuthor.text = book.penulis
                tvBookTitle.text = book.judul
                Glide.with(itemView.context)
                    .load(book.gambar)
                    .into(imgBookCover)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalBookAdapter.HorizontalBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_book, parent, false)
        return HorizontalBookViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HorizontalBookAdapter.HorizontalBookViewHolder,
        position: Int
    ) {
        holder.bind(listBuku[position])
    }

    override fun getItemCount(): Int = listBuku.size
}