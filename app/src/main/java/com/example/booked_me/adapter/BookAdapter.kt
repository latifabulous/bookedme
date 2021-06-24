package com.example.booked_me.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ItemListBinding
import com.example.booked_me.presentation.DetailBookActivity
import com.example.booked_me.uitel.loadImage
import com.squareup.picasso.Picasso

class BookAdapter(var context : Context, var listBook: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(book: Book){
            with(binding){
                tvBookAddress.text = book.alamat
                tvBookTitle.text = book.judul
                tvBookAuthor.text = book.penulis
                tvBookPrice.text = book.harga
//                imgBookCover.loadImage(book.gambar)
                Picasso.get().load(book.gambar).into(imgBookCover)
//                Glide.with(itemView.context)
//                    .load(book.gambar)
//                    .into(imgBookCover)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBookActivity::class.java)
                    intent.putExtra("EXTRA_DATA", book)
                    itemView.context.startActivity(intent)
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBookActivity::class.java)
                    intent.putExtra("EXTRA_DATA", book)
                    itemView.context.startActivity(intent)
                }

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