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
import com.example.booked_me.databinding.ActivityLoginBinding
import com.example.booked_me.presentation.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    companion object{
        const val RC_SIGN_IN = 100
        private var TAG = LoginActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebase: DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        val database = FirebaseDatabase.getInstance()
        firebase = database.getReference("user")
        binding.btnLogIn.setOnClickListener {
            init()
        }
        binding.tvRegis.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun init(){
        var username = binding.etUsername.text.toString()
        var password = binding.etPassword.text.toString()

        if (username.isEmpty()){
            binding.etUsername.error = "Email anda kosong"
            binding.etUsername.requestFocus()
        }
        else if (password.isEmpty()){
            binding.etPassword.error = "Password tidak boleh kosong"
            binding.etPassword.requestFocus()
        }
        else {
            login(username, password)
        }

    }
    private fun login(username:String, password:String){
        firebase.child(username).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user == null){
                    Toast.makeText(this@LoginActivity, "User Not Found", Toast.LENGTH_SHORT).show()
                } else {

                    Log.d("LoginActivity", user.toString())
                    if (username == user.username && password == user.password){
                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } else {
                        Toast.makeText(this@LoginActivity, "Login Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LoginActivity", error.message.toString())
            }

        })
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}