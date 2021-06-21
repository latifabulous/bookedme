package com.example.booked_me.presentation.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.booked_me.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class SettingProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userProfilePict : CircleImageView
    private lateinit var userDOF : TextView
    val REQUEST_CODE = 100

    val calendar = Calendar.getInstance()
    val year = calendar.get((Calendar.YEAR))
    val month = calendar.get((Calendar.MONTH))
    val day = calendar.get((Calendar.DAY_OF_MONTH))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_profile)

        userProfilePict = findViewById(R.id.img_user_pp)
        userDOF = findViewById(R.id.tv_user_dof)



        userProfilePict.setOnClickListener(this)
        userDOF.setOnClickListener(this)
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