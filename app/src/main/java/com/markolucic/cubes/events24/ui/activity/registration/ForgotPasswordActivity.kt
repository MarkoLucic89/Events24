package com.markolucic.cubes.events24.ui.activity.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.databinding.ActivityForgotPasswordBinding
import com.markolucic.cubes.events24.ui.view.CustomToast

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
        setListeners()

    }

    private fun setListeners() {
        binding.buttonSend.setOnClickListener {
            val email = binding.editTextEmail.getText().toString()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email).addOnSuccessListener {
                    finish()
                    CustomToast.showMessage(this, getString(R.string.text_check_email))
                }
            }
        }
    }

    private fun initFirebase() {
        auth = FirebaseAuth.getInstance()
    }
}