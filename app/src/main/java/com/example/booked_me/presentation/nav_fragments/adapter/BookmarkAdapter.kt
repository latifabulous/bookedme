package com.example.booked_me.presentation.nav_fragments.adapter

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ListBookmarkBinding
import com.example.booked_me.presentation.DetailBookActivity

class BookmarkAdapter(private val listBook : List<Book>)
    : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    inner class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ListBookmarkBinding.bind(itemView)

        fun bind(book: Book){
            with(binding){
                tvBookAddress.text = book.alamat
                tvBookAuthor.text = book.penulis
                tvBookPrice.text = "Rp. ${book.harga}"
                tvBookTitle.text = book.judul

                Glide.with(itemView.context)
                    .load(book.gambar)
                    .into(imgBookCover)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBookActivity::class.java)
                    intent.putExtra("EXTRA_DATA", book)
                    itemView.context.startActivity(intent)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(listBook[position])
    }

    override fun getItemCount(): Int {
        return listBook.size
    }
}