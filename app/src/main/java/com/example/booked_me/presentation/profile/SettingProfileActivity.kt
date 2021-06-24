package com.example.booked_me.presentation.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.ActivitySettingProfileBinding
import com.example.booked_me.utils.Preference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap

class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userProfilePict: CircleImageView
    private lateinit var userDOF: TextView

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPhoneNum: EditText
    private lateinit var userStore: EditText
    private lateinit var userAddress: EditText

    private lateinit var btnUpdate: Button
    private lateinit var btnBack: Button
    private lateinit var imageUri: Uri

    val REQUEST_CODE = 100

    lateinit var storage: StorageReference
    private lateinit var binding: ActivitySettingProfileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var preference: Preference

    val calendar = Calendar.getInstance()
    val year = calendar.get((Calendar.YEAR))
    val month = calendar.get((Calendar.MONTH))
    val day = calendar.get((Calendar.DAY_OF_MONTH))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)
        userProfilePict = findViewById(R.id.img_user_pp)
        userDOF = findViewById(R.id.tv_user_dof)

        userName = findViewById(R.id.et_user_name)
        userEmail = findViewById(R.id.et_user_email)
        userPhoneNum = findViewById(R.id.et_user_phone)
        userStore = findViewById(R.id.et_user_store)
        userAddress = findViewById(R.id.et_user_address)

        btnUpdate = findViewById(R.id.btn_update)
        btnBack = findViewById(R.id.btn_back)

        userProfilePict.setOnClickListener(this)
        userDOF.setOnClickListener(this)
        btnUpdate.setOnClickListener(this)

        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        storage = FirebaseStorage.getInstance().reference.child("profileImg/")

        readData()

    }

    private fun readData() {
        databaseReference.child(preference.getValue("username").toString()).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)

                    with(binding) {
                        etUserName.setText(user?.username)
                        etUserEmail.setText(user?.email)
                        etUserAddress.setText(user?.address)
                        etUserPhone.setText(user?.phone)
                        etUserStore.setText(user?.store)
                        tvUserDof.text = user?.date

                        Picasso.get()
                            .load(user?.photo)
                            .into(imgUserPp)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("SettingProfileActivity", error.message)
                }

            }
        )
    }

    private fun updateData() {
        var date = userDOF.text.toString()
        var store = userStore.text.toString()
        var phone = userPhoneNum.text.toString()
        var email = userEmail.text.toString()
        var alamat = userAddress.text.toString()
        var username = userName.text.toString()



        if (username.isEmpty()) {
            userName.error = "Field ini kosong"
        } else if (email.isEmpty()) {
            userEmail.error = "Field ini kosong"
        } else if (phone.isEmpty()) {
            userPhoneNum.error = "Field ini kosong"
        } else if (store.isEmpty()) {
            userStore.error = "Field ini kosong"
        } else if (alamat.isEmpty()) {
            userAddress.error = "Field ini kosong"
        } else if (date.isEmpty()) {
            userDOF.error = "Field ini kosong"
        } else {
            updateUser(username, alamat, store, email, date, phone)
        }
    }

    private fun updateUser(
        username: String,
        alamat: String,
        store: String,
        email: String,
        date: String,
        phone: String
    ) {
//        val user = User()
//        user.username = username
//        user.address = alamat
//        user.store = store
//        user.date = date
//        user.email = email
//        user.phone = phone

        val dataUser = hashMapOf<String, Any>(
            "address" to alamat,
            "date" to date,
            "store" to store,
            "username" to username,
            "email" to email,
            "phone" to phone
        )

        databaseReference.child(preference.getValue("username").toString()).updateChildren(dataUser)
            .addOnCompleteListener {
                Toast.makeText(this, "Update data Success", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_user_pp -> {
                val intentGallery = Intent(Intent.ACTION_PICK)
                intentGallery.type = "image/*"
                startActivityForResult(intentGallery, REQUEST_CODE)
            }
            R.id.tv_user_dof -> {
                val dpd = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        userDOF.setText("" + mDay + "/" + mMonth + "/" + mYear)
                    },
                    year,
                    month,
                    day
                )

                dpd.show()
            }


            R.id.btn_update -> {
                updateData()
            }
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            val imageData = data!!.getData()
            val imageName: StorageReference = storage.child("image" + imageData!!.getLastPathSegment())


            imageName.putFile(imageData).addOnSuccessListener {
                userProfilePict.setImageURI(data?.data)
                imageName.getDownloadUrl().addOnSuccessListener { uri ->
                    val databaseReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference("user").child(preference.getValue("username").toString())

                    val hashMap: HashMap<String, Any> = hashMapOf(
                        "photo" to uri.toString()
                    )
                    databaseReference.updateChildren(hashMap)

                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}

