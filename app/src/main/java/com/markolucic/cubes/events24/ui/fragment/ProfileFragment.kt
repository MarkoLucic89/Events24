package com.markolucic.cubes.events24.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.databinding.FragmentProfileBinding
import com.markolucic.cubes.events24.ui.activity.SplashScreenActivity
import com.markolucic.cubes.events24.ui.view.CustomToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var prefs: DataStorePrefs
    private var languageIndex = 0
    private var isNotificationOn = true
    private lateinit var languages: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        prefs = DataStorePrefs(requireContext().applicationContext)

        languages = resources.getStringArray(R.array.languages)

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNotifications()
        observeLanguage()
        setListeners()
    }

    private fun observeNotifications() {
        prefs.isNotificationOn().asLiveData().observe(requireActivity()) {
            isNotificationOn = it
            binding.switchNotifications.isChecked = isNotificationOn

            if (isNotificationOn) {
                FirebaseMessaging.getInstance().subscribeToTopic("general")
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("general")
            }
        }
    }

    private fun observeLanguage() {
        prefs.getLanguage().asLiveData().observe(requireActivity()) {
            languageIndex = it
            binding.buttonLanguage.text = languages[it]
        }
    }

    private fun setListeners() {
        binding.buttonLogout.setOnClickListener { logout() }
        binding.textViewHelpCentre.setOnClickListener {
            CustomToast.showMessage(requireContext(),
                "In progress...")
        }
        binding.textViewTermsAndConditions.setOnClickListener {
            CustomToast.showMessage(requireContext(),
                "In progress...")
        }
        binding.buttonLanguage.setOnClickListener { handleLanguage() }
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            run {
                CoroutineScope(IO).launch {
                    prefs.setNotificationStatus(isChecked)
                }
            }
        }
    }

    private fun handleLanguage() {
        if (languageIndex == languages.lastIndex) {
            CoroutineScope(IO).launch { prefs.saveLanguage(0) }
        } else {
            languageIndex++
            CoroutineScope(IO).launch { prefs.saveLanguage(languageIndex) }
        }
    }

    private fun logout() {
        auth.signOut()
        activity?.finish()
        startActivity(Intent(activity, SplashScreenActivity::class.java))
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}