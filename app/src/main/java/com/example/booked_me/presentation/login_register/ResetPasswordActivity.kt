package com.example.booked_me.presentation.login_register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.booked_me.R
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var btnResetPassword : Button
    private lateinit var etEmail : EditText

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btnResetPassword = findViewById(R.id.btn_reset_pass)
        etEmail = findViewById(R.id.et_email)

        auth = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener{
            val email = etEmail.text.toString().trim()

            if(email.isEmpty()) {
                etEmail.error = "Email adress is required"
                etEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.error = "Invalid email"
                etEmail.requestFocus()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,LoginActivity::class.java))

                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}