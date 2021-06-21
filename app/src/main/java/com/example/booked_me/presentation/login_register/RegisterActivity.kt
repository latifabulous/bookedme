package com.example.booked_me.presentation.login_register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.ActivityRegisterBinding
import com.google.firebase.database.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        firebase = database.getReference("user")

        binding.btnLogIn.setOnClickListener {
            init()
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun init(){
        var username = binding.etUname.text.toString()
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()

        if(username.isEmpty()){
            binding.etUname.error = "Harap isi username anda"
            binding.etUname.requestFocus()
        } else if(email.isEmpty()){
            binding.etEmail.error = "Harap isi email anda"
            binding.etEmail.requestFocus()
        } else if(password.isEmpty()){
            binding.etPassword.error = "Harap isi password anda"
            binding.etPassword.requestFocus()
        } else {
            register(username, email, password)
        }
    }

    private fun register(username: String, email: String, password: String) {
        val dataUser = User(username = username, email = email, password = password)

        firebase.child(username).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                if (user == null){
                    firebase.child(username).setValue(dataUser)

                    Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "User has been added", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RegisterActivity", error.message)
            }

        })

    }
}