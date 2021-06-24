package com.example.booked_me.uitel

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.booked_me.R

fun ImageView.loadImage(url: String?){

    val option = RequestOptions().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}