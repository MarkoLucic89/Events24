package com.markolucic.cubes.events24.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.databinding.ActivitySplashScreenBinding
import com.markolucic.cubes.events24.ui.activity.registration.MainRegistrationActivity
import com.markolucic.cubes.events24.ui.view.CustomToast
import java.util.*

class SplashScreenActivity : BasicActivity() {

    private val TAG: String = "SplashScreenActivity"
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        initToken()

        binding.imageViewLogo.postDelayed({
            if (mAuth.currentUser == null) {
                startActivity(Intent(applicationContext, MainRegistrationActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
            finish()
        }, 500)
    }

    private fun initToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

//            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAnalytics = FirebaseAnalytics.getInstance(this)

    }

}