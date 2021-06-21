package com.example.booked_me.presentation.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.booked_me.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap

class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userProfilePict : CircleImageView
    private lateinit var userDOF : TextView

    private lateinit var userName        : EditText
    private lateinit var userEmail       : EditText
    private lateinit var userPhoneNum    : EditText
    private lateinit var userStore       : EditText
    private lateinit var userAddress     : EditText

    private lateinit var btnSaved        : Button

    private lateinit var imageUri        : Uri

    val REQUEST_CODE = 100

    lateinit var storage : StorageReference

    val calendar = Calendar.getInstance()
    val year = calendar.get((Calendar.YEAR))
    val month = calendar.get((Calendar.MONTH))
    val day = calendar.get((Calendar.DAY_OF_MONTH))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_profile)

        userProfilePict = findViewById(R.id.img_user_pp)
        userDOF = findViewById(R.id.tv_user_dof)

        userName        = findViewById(R.id.et_user_name)
        userEmail       = findViewById(R.id.et_user_email)
        userPhoneNum    = findViewById(R.id.et_user_phone)
        userStore       = findViewById(R.id.et_user_store)
        userAddress     = findViewById(R.id.et_user_address)

        btnSaved        = findViewById(R.id.btn_log_in)

        userProfilePict.setOnClickListener(this)
        userDOF.setOnClickListener(this)
        btnSaved.setOnClickListener(this)


        storage = FirebaseStorage.getInstance().reference.child("profileImg/")

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_user_pp -> {
                val intentGallery = Intent(Intent.ACTION_PICK)
                intentGallery.type = "image/*"
                startActivityForResult(intentGallery, REQUEST_CODE)
            }
            R.id.tv_user_dof -> {
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                     userDOF.setText("" + mDay + "/" + mMonth + "/" + mYear)
                }, year, month, day)

                dpd.show()
            }

            R.id.btn_log_in-> {
                
            }




        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){


            val imageData = data!!.getData()
            val imageName:StorageReference = storage.child("image" + imageData!!.getLastPathSegment())


            imageName.putFile(imageData).addOnSuccessListener {
                userProfilePict.setImageURI(data?.data)
                imageName.getDownloadUrl().addOnSuccessListener { uri ->
                    val databaseReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReferenceFromUrl("https://booked-me-default-rtdb.firebaseio.com/").child("profilImg")
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("imageUrl", uri.toString())
                    databaseReference.setValue(hashMap)
                    Toast.makeText(this,"Profile Updated", Toast.LENGTH_SHORT).show()


                }
            }

        }
    }

}
