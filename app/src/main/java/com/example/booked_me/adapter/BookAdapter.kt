package com.example.booked_me.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ItemListBinding
import com.example.booked_me.uitel.loadImage
import com.squareup.picasso.Picasso

class BookAdapter(private var listBook : ArrayList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(book: Book) {
            with(binding) {
                tvBookAddress.text = book.alamat
                tvBookTitle.text = book.judul
                tvBookAuthor.text = book.penulis
                tvBookPrice.text = book.harga
                Glide.with(itemView.context)
                        .load(book.gambar)
                        .into(imgBookCover)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(listBook[position])
    }

    override fun getItemCount(): Int = listBook.size
}