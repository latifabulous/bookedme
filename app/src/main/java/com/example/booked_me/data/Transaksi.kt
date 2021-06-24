package com.example.booked_me.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Transaksi (
    var judul_buku : String? = null,
    var harga: String?= null,
    var user : String? = null,
    var phone:String? =null,
    var alamat_user:String? = null,
    var date:String? = null,
    var status:String? = null

) : Parcelable