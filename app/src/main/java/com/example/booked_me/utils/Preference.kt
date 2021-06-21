package com.example.booked_me.utils

import android.content.Context

class Preference (context : Context) {

    val pref = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    val edit = pref.edit()

    fun setValue(key : String, value: String){
        edit.putString(key, value).commit()
    }

    fun getValue(key : String) : String? {
        return pref.getString(key, "")
    }
}