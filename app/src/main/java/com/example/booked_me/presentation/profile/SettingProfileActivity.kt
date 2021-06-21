package com.example.booked_me.presentation.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.ActivitySettingProfileBinding
import com.example.booked_me.utils.Preference
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userProfilePict : CircleImageView
    private lateinit var userDOF : TextView
    private lateinit var binding : ActivitySettingProfileBinding
    private lateinit var database : DatabaseReference
    private lateinit var preference : Preference
    val REQUEST_CODE = 100

    val calendar = Calendar.getInstance()
    val year = calendar.get((Calendar.YEAR))
    val month = calendar.get((Calendar.MONTH))
    val day = calendar.get((Calendar.DAY_OF_MONTH))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)
        database = FirebaseDatabase.getInstance().getReference("user")

        userProfilePict = findViewById(R.id.img_user_pp)
        userDOF = findViewById(R.id.tv_user_dof)

        userProfilePict.setOnClickListener(this)
        userDOF.setOnClickListener(this)

        getData()
    }

    private fun getData() {
        var username = preference.getValue("username")
        database.child(username.toString()).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                with(binding){
                    Glide.with(this@SettingProfileActivity)
                            .load(user?.photo)
                            .circleCrop()
                            .into(imgUserPp)

                    etUserName.setText(user?.username.toString())
                    etUserAddress.setText(user?.address.toString())
                    etUserEmail.setText(user?.email.toString())
                    etUserPhone.setText(user?.phone.toString())
                    etUserStore.setText(user?.store.toString())
                    tvUserDof.text = user?.date.toString()
                    etUserStore.setText(user?.store.toString())

                    if (rbUserFemale.text == user?.gender){
                        rbUserFemale.isChecked
                    } else {
                        rbUserMale.isChecked
                    }

                    
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            userProfilePict.setImageURI(data?.data)
        }
    }
}