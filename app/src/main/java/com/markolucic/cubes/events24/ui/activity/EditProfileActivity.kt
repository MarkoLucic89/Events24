package com.markolucic.cubes.events24.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.DataContainer
import com.markolucic.cubes.events24.data.model.User
import com.markolucic.cubes.events24.databinding.ActivityEditProfileBinding
import com.markolucic.cubes.events24.ui.view.CustomToast
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class EditProfileActivity : BasicActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    //firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mFirebaseStorage: FirebaseStorage

    //vars
    private lateinit var mUserId: String
    private lateinit var mUserDocumentId: String

    private var mSelectedImageFromGallery: Uri? = null
    private var mSelectedImageFromCamera: ByteArray? = null

    companion object {
        private const val REQUEST_CODE_GALLERY = 1111
        private const val REQUEST_CODE_CAMERA = 1112
        private const val CAMERA_PERMISSION_CODE = 1113
        private const val STORAGE_PERMISSION_CODE = 1114
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        setCurrentUser()
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {

            mSelectedImageFromGallery = data!!.data
            Picasso.get().load(mSelectedImageFromGallery.toString())
                .into(binding.imageViewProfileImage)

        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {

            val bitmap = data!!.extras!!.get("data") as Bitmap
            binding.imageViewProfileImage.setImageBitmap(bitmap)

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            mSelectedImageFromCamera = baos.toByteArray()

        }
    }

    private fun uploadImageFromGallery() {

        val profileFolderName = mAuth.uid.toString()
        val imageName = "${System.currentTimeMillis()}.jpg"

        FirebaseStorage.getInstance().reference
            .child("images/profile/$profileFolderName/$imageName")
            .putFile(mSelectedImageFromGallery!!)
            .addOnSuccessListener {

                it.metadata!!
                    .reference!!
                    .downloadUrl
                    .addOnSuccessListener {

                        DataContainer.user!!.imageUrl = it.toString()

                        uploadUserToFirestore()

                    }

            }.addOnFailureListener {

                CustomToast.showMessage(applicationContext, it.localizedMessage)

            }

    }

    private fun uploadImageFromCamera() {

        val profileFolderName = mAuth.uid.toString()
        val imageName = "${System.currentTimeMillis()}.jpg"

        FirebaseStorage.getInstance().reference
            .child("images/profile/$profileFolderName/$imageName")
            .putBytes(mSelectedImageFromCamera!!)
            .addOnSuccessListener {

                it.metadata!!
                    .reference!!
                    .downloadUrl
                    .addOnSuccessListener {

                        DataContainer.user!!.imageUrl = it.toString()

                        uploadUserToFirestore()

                    }

            }.addOnFailureListener {

                CustomToast.showMessage(applicationContext, it.localizedMessage)

            }

    }

    private fun setListeners() {
        binding.imageViewProfileImage.setOnClickListener { pickImage() }
        binding.buttonSave.setOnClickListener { saveUserChanges() }
    }

    private fun pickImage() {

        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.dialog_pick_image_title))
            setMessage(getString(R.string.dialog_pick_image_message))
            setPositiveButton(getString(R.string.dialog_pick_image_gallery)) { _, _ ->
                run {

                    if (hasStoragePermission()) {
                        openGallery()
                    } else {
                        requestReadExternalStoragePermission()
                    }

                }
            }
            setNegativeButton(getString(R.string.dialog_pick_image_camera)) { _, _ ->
                run {

                    if (hasCameraPermission()) {
                        openCamera()
                    } else {
                        requestCameraPermission()
                    }

                }
            }
            setCancelable(true)
            show()
        }

    }

    private fun hasCameraPermission(): Boolean = ContextCompat.checkSelfPermission(this,
        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun hasStoragePermission(): Boolean = ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun requestReadExternalStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {


            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_permission_title))
                setMessage(getString(R.string.dialog_permission_storage_message))
                setPositiveButton(getString(R.string.ok)) { _, _ ->
                    run {

                        ActivityCompat.requestPermissions(
                            this@EditProfileActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            STORAGE_PERMISSION_CODE
                        )

                    }
                }
                setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                setCancelable(true)
                create()
                show()
            }


        } else {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )

        }
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)
        ) {

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.dialog_permission_title))
                setMessage(getString(R.string.dialog_permission_camera_message))
                setPositiveButton(getString(R.string.ok)) { _, _ ->
                    run {

                        ActivityCompat.requestPermissions(
                            this@EditProfileActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_CODE
                        )

                    }
                }
                setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
                setCancelable(true)
                create()
                show()
            }


        } else {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )

        }
    }


    private fun openCamera() {
//        capturePictureLauncher.launch(mUri)

        mSelectedImageFromGallery = null

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    private fun openGallery() {
//        pickImageLauncher.launch("image/*")

        mSelectedImageFromCamera = null

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }

    }


    private fun saveUserChanges() {
        if (mSelectedImageFromGallery != null) {
            uploadImageFromGallery()
        } else if (mSelectedImageFromCamera != null) {
            uploadImageFromCamera()
        }
    }


    private fun uploadUserToFirestore() {
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
                CustomToast.showMessage(applicationContext, getString(R.string.toast_user_successfully_uploaded))
                finish()

            }
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
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
        if (user.imageUrl.isNotEmpty()) {
            Picasso.get().load(user.imageUrl).into(binding.imageViewProfileImage)
        }
    }
}