package com.markolucic.cubes.events24.ui.activity.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.markolucic.cubes.events24.databinding.ActivityRegistrationBinding
import com.markolucic.cubes.events24.ui.activity.HomeActivity
import com.markolucic.cubes.events24.ui.view.CustomToast

class RegistrationActivity : AppCompatActivity() {
    private val TAG = "RegistrationActivity"

    private lateinit var binding: ActivityRegistrationBinding

    //firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAnalytics: FirebaseAnalytics
    private lateinit var mFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        setListeners()
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAnalytics = FirebaseAnalytics.getInstance(this)
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun setListeners() {
        binding.buttonLogin.setOnClickListener { login() }
        binding.buttonRegistration.setOnClickListener { register() }
        binding.textViewForgotPassword.setOnClickListener { forgotPassword() }
    }

    private fun forgotPassword() {
        startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
        finish()
    }

    private fun login() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    private fun updateLoadingUi(isLoading: Boolean) {

        binding.editTextEmail.isEnabled = !isLoading
        binding.editTextPassword.isEnabled = !isLoading
        binding.editTextConfirmPassword.isEnabled = !isLoading
        binding.buttonRegistration.isEnabled = !isLoading
        binding.buttonLogin.isEnabled = !isLoading

        if (isLoading) {
            binding.buttonRegistration.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.buttonRegistration.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun register() {
        val email = binding.editTextEmail.getText().toString()
        val password = binding.editTextPassword.getText().toString()
        val confirmPassword = binding.editTextConfirmPassword.getText().toString()


        if (email.isEmpty()) {
            CustomToast.showMessage(this, "Email must be filled out!")
            return
        }

        if (password.isEmpty()) {
            CustomToast.showMessage(this, "Password must be filled out!")
            return
        }

        if (confirmPassword.isEmpty()) {
            CustomToast.showMessage(this, "Password must be confirmed!")
            return
        }

        if (password != confirmPassword) {
            CustomToast.showMessage(this, "Your password and confirmation password do not match.")
            return
        }

        updateLoadingUi(true)

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateLoadingUi(false)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }.addOnFailureListener {
                updateLoadingUi(false)
                CustomToast.showMessage(this, it.message.toString())
                Log.d(TAG, "register: ${it.message}")
            }

    }
}