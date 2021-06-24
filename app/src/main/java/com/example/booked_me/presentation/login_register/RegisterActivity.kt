package com.example.booked_me.presentation.login_register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
//    private lateinit var tvForgotPassword : TextView

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebase: DatabaseReference

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        tvForgotPassword = findViewById(R.id.tv_forgot_pass)
//
//        tvForgotPassword.setOnClickListener{
//            startActivity(Intent(this,ResetPasswordActivity::class.java))
//        }

        auth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance()
        firebase = database.getReference("user")

        binding.cbPolicy.setOnClickListener{
            if (binding.cbPolicy.isChecked){
                binding.btnLogIn.visibility = View.VISIBLE
                binding.btnLogIn.isEnabled = true
            } else {
                binding.btnLogIn.visibility = View.INVISIBLE
                binding.btnLogIn.isEnabled = false
            }
        }

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
            binding.etUname.error = "Fill username"
            binding.etUname.requestFocus()
        } else if(email.isEmpty()){
            binding.etEmail.error = "Fill email"
            binding.etEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email invalid"
            binding.etEmail.requestFocus()
        } else if(password.isEmpty() || password.length < 6){
            binding.etPassword.error = "Password invalid"
        } else {
            register(username, email, password)
        }
    }

    private fun register(username: String, email: String, password: String) {
//
        val dataUser = User(username = username, email = email, password = password)

        firebase.child(username).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                if (user == null){
                    firebase.child(username).setValue(dataUser)


                    Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "User has been added", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RegisterActivity", error.message)
            }

        })

    }
}