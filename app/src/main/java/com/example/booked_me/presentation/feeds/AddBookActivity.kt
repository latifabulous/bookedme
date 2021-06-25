package com.example.booked_me.presentation.feeds

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.data.User
import com.example.booked_me.utils.Preference
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class AddBookActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBack : Button
    private lateinit var etTitle : EditText
    private lateinit var etAuthor : EditText
    private lateinit var etSummary : EditText
    private lateinit var etLanguage : EditText
    private lateinit var etNumPages : EditText
    private lateinit var etPrice : EditText
    private lateinit var ivCover : ImageView
    private lateinit var btnCancel : Button

    private lateinit var pbAdd : ProgressBar

    private var PICK_IMAGE_REQUEST = 1
    private lateinit var imageUri : Uri

    private var myUrl = ""

    private var databaseReference: DatabaseReference? = null
    private var storageReference : StorageReference? = null
    private lateinit var preference: Preference
    private lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        preference = Preference(this)

        etTitle = findViewById(R.id.et_book_title)
        etAuthor = findViewById(R.id.et_book_author)
        etSummary = findViewById(R.id.et_book_summary)
        etLanguage = findViewById(R.id.et_book_lang)
        etNumPages = findViewById(R.id.et_book_pages)
        etPrice = findViewById(R.id.et_book_price)
        ivCover = findViewById(R.id.img_book_cover)
        pbAdd = findViewById(R.id.pb_add)

        btnBack = findViewById(R.id.btn_back)
        databaseReference = FirebaseDatabase.getInstance().getReference().child("buku")
        storageReference = FirebaseStorage.getInstance().reference.child("buku")

        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)

        btnBack.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        ivCover.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_save -> {
                if (imageUri != null) {
                    uploadFile()
                }else{
                    Toast.makeText(this@AddBookActivity, "Select an Image", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.img_book_cover -> {
                openFileChose()

            }
            R.id.btn_back -> {
                super.onBackPressed()
            }
        }
    }

    private fun openFileChose() {
        val intentGallery = Intent()
        intentGallery.action = Intent.ACTION_GET_CONTENT
        intentGallery.type = "image/*"
        startActivityForResult(intentGallery, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            ivCover.setImageURI(imageUri)
        }
    }

    private fun getFileExtension(uri : Uri) : String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }

    private fun uploadFile() {
        val fileReference = storageReference!!.child(
            System.currentTimeMillis()
                .toString() + "." + ".png"
        )

        pbAdd.visibility = View.VISIBLE
        pbAdd.isIndeterminate = true

        var upTask :StorageTask<*>? = null

        upTask = fileReference.putFile(imageUri!!)

        upTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
            if (!task.isSuccessful)
            {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation fileReference.downloadUrl
        }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result
                myUrl = downloadUrl.toString()
                val ref = FirebaseDatabase.getInstance().reference.child("buku")

                val handler = Handler()
                handler.postDelayed(
                    {
                        pbAdd.visibility = View.VISIBLE
                        pbAdd.isIndeterminate = false
                        pbAdd.progress = 0
                    }, 500
                )

                var judul = etTitle.text.toString().trim()
                var penulis = etAuthor.text.toString().trim()
                var deskripsi = etSummary.text.toString().trim()
                var bahasa = etLanguage.text.toString().trim()
                var halaman = etNumPages.text.toString().trim()
                var harga = etPrice.text.toString().trim()
                var gambar = myUrl

//
//                if (judul.isEmpty()){
//                    etTitle.error = "Field ini kosong"
//                }

                val buku = Book()
                buku.judul = judul
                buku.penulis = penulis
                buku.halaman = halaman
                buku.deskripsi = deskripsi
                buku.harga = harga
                buku.bahasa = bahasa
                buku.rating = "0"
                buku.gambar = gambar
                buku.userInput = preference.getValue("username")

                if (judul.isEmpty()){
                    etTitle.error = "field cannot be empty"
                }else if (penulis.isEmpty()){
                    etAuthor.error = "field cannot be empty"
                }else if (halaman.isEmpty()){
                    etNumPages.error = "field cannot be empty"
                }else if (deskripsi.isEmpty()){
                    etSummary.error = "field cannot be empty"
                }else if (harga.isEmpty()){
                    etPrice.error = "field cannot be empty"
                }else if (bahasa.isEmpty()){
                    etLanguage.error = "field cannot be empty"
                }else {
                    databaseReference!!.child((judul)!!).setValue(buku)
                    pbAdd.visibility = View.INVISIBLE

                    Toast.makeText(this@AddBookActivity, "Book has been added", Toast.LENGTH_LONG).show()

                    openImageActivity()
                }

//                databaseReference!!.child((judul)!!).setValue(buku)
//                pbAdd.visibility = View.INVISIBLE
//                openImageActivity()
            } else {
                Toast.makeText(
                    this@AddBookActivity,
                    "You haven't select Any File",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        )
    }

    private fun openImageActivity() {
        finish()
        startActivity(Intent(this@AddBookActivity, MyFeedsActivity::class.java))
    }
}