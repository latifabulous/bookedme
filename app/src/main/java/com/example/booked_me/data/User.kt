package com.example.booked_me.data

data class User(
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var photo : String? = null,
    var store : String? = null,
    var address : String? = null,
    var gender : String? = null,
    var date : String? = null,
    var phone : String? = null
)