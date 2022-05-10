package com.markolucic.cubes.events24.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.markolucic.cubes.events24.databinding.ActivitySplashScreenBinding
import com.markolucic.cubes.events24.ui.activity.registration.MainRegistrationActivity
import com.markolucic.cubes.events24.ui.view.CustomToast

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()

        binding.imageViewLogo.postDelayed({
            if (mAuth.currentUser == null) {
                startActivity(Intent(applicationContext, MainRegistrationActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
            finish()
        }, 500)
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAnalytics = FirebaseAnalytics.getInstance(this)

    }

}