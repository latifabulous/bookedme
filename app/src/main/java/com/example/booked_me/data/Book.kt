package com.example.booked_me.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    var alamat : String? = null,
    var bahasa : String? = null,
    var deskripsi : String? = null,
    var gambar : String? = null,
    var halaman : String? = null,
    var harga : String? = null,
    var judul : String? = null,
    var penulis : String? = null,
    var rating : String? = null,
    var userInput : String? = null,
    var isBookmark : Boolean = false,
    @get:Exclude
    @set:Exclude
    var key:String? = null
    ) : Parcelable
