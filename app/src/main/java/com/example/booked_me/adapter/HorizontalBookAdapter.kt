package com.example.booked_me.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.databinding.ListBookBinding
import com.example.booked_me.presentation.DetailBookActivity
import com.squareup.picasso.Picasso

class HorizontalBookAdapter(private var listBuku : List<Book>)
    : RecyclerView.Adapter<HorizontalBookAdapter.HorizontalBookViewHolder>() {
    class HorizontalBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListBookBinding.bind(itemView)
        fun bind(book: Book){
            with(binding){
                tvBookAuthor.text = book.penulis
                tvBookTitle.text = book.judul
                Picasso.get().load(book.gambar).into(imgBookCover)
//                Glide.with(itemView.context)
//                    .load(book.gambar)
//                    .into(imgBookCover)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailBookActivity::class.java)
                    intent.putExtra("EXTRA_DATA", book)
                    itemView.context.startActivity(intent)
                }
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

    override fun onBindViewHolder(holder: HorizontalBookAdapter.HorizontalBookViewHolder, position: Int) {
        holder.bind(listBuku[position])
    }

    override fun getItemCount(): Int = listBuku.size
}