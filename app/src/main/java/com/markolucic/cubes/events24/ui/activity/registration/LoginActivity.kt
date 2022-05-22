package com.markolucic.cubes.events24.ui.activity.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.databinding.ActivityLoginBinding
import com.markolucic.cubes.events24.ui.activity.BasicActivity
import com.markolucic.cubes.events24.ui.activity.HomeActivity
import com.markolucic.cubes.events24.ui.view.CustomToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class LoginActivity : BasicActivity() {
    private val TAG = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding

    //datastore
    private lateinit var prefs: DataStorePrefs

    //firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAnalytics: FirebaseAnalytics
    private lateinit var mFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = DataStorePrefs(applicationContext)
        prefs.getEmail().asLiveData().observe(this) {
            binding.editTextEmail.setText(it)
        }


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
        val email = binding.editTextEmail.getText().toString()
        val password = binding.editTextPassword.getText().toString()

        if (email.isEmpty()) {
            CustomToast.showMessage(this, "Email is empty, please enter all fields!")
            return
        }

        if (password.isEmpty()) {
            CustomToast.showMessage(this, "Password is empty, please enter all fields!")
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    CoroutineScope(IO).launch {
                        prefs.saveEmail(email)
                        prefs.setNotificationStatus(true)
                        FirebaseMessaging.getInstance().subscribeToTopic("general")
                    }

                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Log.d(TAG, "login: Exception -> ${it.exception?.message}")
                    CustomToast.showMessage(this, "ERROR: ${it.exception?.message}")
                }
            }


    }

    private fun register() {
        startActivity(Intent(applicationContext, RegistrationActivity::class.java))
        finish()
    }
}