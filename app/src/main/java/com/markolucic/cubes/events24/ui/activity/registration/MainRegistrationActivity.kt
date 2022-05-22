package com.markolucic.cubes.events24.ui.activity.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.markolucic.cubes.events24.databinding.ActivityMainRegistrationBinding
import com.markolucic.cubes.events24.ui.activity.BasicActivity

class MainRegistrationActivity : BasicActivity() {

    private lateinit var binding: ActivityMainRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

    }

    private fun setListeners() {
        binding.buttonLogin.setOnClickListener { login() }
        binding.buttonRegistration.setOnClickListener { registration() }
    }

    private fun registration() {
        val intent = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }
}