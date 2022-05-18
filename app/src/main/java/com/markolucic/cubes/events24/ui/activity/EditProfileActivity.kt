package com.markolucic.cubes.events24.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.markolucic.cubes.events24.data.DataContainer
import com.markolucic.cubes.events24.data.model.User
import com.markolucic.cubes.events24.databinding.ActivityEditProfileBinding
import com.markolucic.cubes.events24.ui.view.CustomToast
import com.squareup.picasso.Picasso


class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    //firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    //vars
    private lateinit var mUserId: String
    private lateinit var mUserDocumentId: String

    private var mUri: Uri? = null

    companion object {
        private const val REQUEST_GALLERY = 1
        private const val REQUEST_CAMERA = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        setCurrentUser()
        setListeners()
    }

    private fun setListeners() {
        binding.imageViewProfileImage.setOnClickListener { pickImage() }
        binding.buttonSave.setOnClickListener { saveUser() }
    }

    private fun pickImage() {

        AlertDialog.Builder(this).apply {
            setTitle("Pick image")
            setMessage("Please, select image destination")
            setPositiveButton("Gallery") { _, _ ->
                run {
//                    pickImageLauncher.launch("image/*")

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_GALLERY)
                }
            }
            setNegativeButton("Camera") { _, _ ->
                run {
//                    capturePictureLauncher.launch(mUri)
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
            setCancelable(true)
            show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            mUri = data!!.data
            Picasso.get().load(mUri.toString()).into(binding.imageViewProfileImage)

        }

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {

            val bitmap = data!!.extras!!.get("data") as Bitmap
            binding.imageViewProfileImage.setImageBitmap(bitmap)
        }
    }

//    private val pickImageLauncher: ActivityResultLauncher<String> =
//        registerForActivityResult(ActivityResultContracts.GetContent()) {
//            mUri = it
//            Picasso.get().load(it).into(binding.imageViewProfileImage)
//
//        }
//
//    private val capturePictureLauncher =
//        registerForActivityResult(ActivityResultContracts.TakePicture()) {
//            if (it) {
//                Picasso.get().load(mUri).into(binding.imageViewProfileImage)
//            }
//        }


    private fun saveUser() {
        val updatedUser = DataContainer.user

        updatedUser!!.apply {
            name = binding.editTextName.getText().toString()
            surname = binding.editTextSurname.getText().toString()
            address = binding.editTextAddress.getText().toString()
            city = binding.editTextCity.getText().toString()
            phone = binding.editTextPhone.getText().toString()
        }

        mFirestore
            .collection("users")
            .document(mUserDocumentId)
            .set(updatedUser)
            .addOnSuccessListener {
                updateUserUi(updatedUser)
                finish()
            }
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }


    private fun setCurrentUser() {

        mUserId = mAuth.currentUser!!.uid

        mFirestore
            .collection("users")
            .whereEqualTo("id", mUserId)
            .get()
            .addOnSuccessListener {

                for (user: User in it.toObjects(User::class.java)) {
                    if (mUserId == user.id) {

                        mUserDocumentId = it.documents[0].id

                        DataContainer.user = user

                        updateUserUi(user)

                        break
                    }
                }
            }

    }

    private fun updateUserUi(user: User) {
        binding.editTextName.setText(user.name)
        binding.editTextSurname.setText(user.surname)
        binding.editTextAddress.setText(user.address)
        binding.editTextCity.setText(user.city)
        binding.editTextPhone.setText(user.phone)
    }
}