package com.example.booked_me.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Transaksi(
    var judul_buku : String? = null,
    var price : String? = null,
    var user : String? = null,
    var phone : String? = null,
    var address_user : String? = null,
    var date : String? = null,
    var gambar : String? = null

) : Parcelable