package com.markolucic.cubes.events24.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.DataContainer
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.data.model.User
import com.markolucic.cubes.events24.databinding.FragmentProfileBinding
import com.markolucic.cubes.events24.tools.LanguageTools
import com.markolucic.cubes.events24.ui.activity.EditProfileActivity
import com.markolucic.cubes.events24.ui.activity.HomeActivity
import com.markolucic.cubes.events24.ui.activity.SplashScreenActivity
import com.markolucic.cubes.events24.ui.view.CustomToast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var prefs: DataStorePrefs
    private var languageIndex = 0
    private lateinit var mUserId: String
    private var isNotificationOn = true
    private lateinit var languages: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        prefs = DataStorePrefs(requireContext().applicationContext)

        languages = resources.getStringArray(R.array.languages)

        mAuth = FirebaseAuth.getInstance()

        mFirestore = FirebaseFirestore.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNotifications()
        observeLanguage()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        setCurrentUser()
    }

    override fun onStop() {
        super.onStop()
        DataStorePrefs(requireContext()).getLanguage().asLiveData().removeObservers(this)

    }

    private fun setCurrentUser() {

        if (DataContainer.user == null) {

            mUserId = mAuth.currentUser!!.uid

            mFirestore
                .collection("users")
                .whereEqualTo("id", mUserId)
                .get()
                .addOnSuccessListener {

                    for (user: User in it.toObjects(User::class.java)) {
                        if (mUserId == user.id) {

                            DataContainer.user = user

                            updateUserUi(user)

                            break
                        }
                    }
                }

        } else {

            updateUserUi(DataContainer.user!!)

        }
    }

    private fun updateUserUi(user: User) {

        if (user.imageUrl.isNotEmpty()) {
            Picasso.get().load(user.imageUrl).into(binding.imageViewProfileImage)
        }

        binding.textViewEmail.text = user.email
        binding.textViewName.text = user.name

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

            binding.buttonLanguage.text = languages[languageIndex]

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
        binding.cardViewImage.setOnClickListener { goToEditProfileActivity() }
        binding.textViewName.setOnClickListener { goToEditProfileActivity() }
        binding.textViewEmail.setOnClickListener { goToEditProfileActivity() }
    }

    private fun goToEditProfileActivity() {
        startActivity(Intent(requireContext(), EditProfileActivity::class.java))
    }

    private fun handleLanguage() {

        if (languageIndex == languages.lastIndex) {

            CoroutineScope(IO).launch {
                prefs.saveLanguage(0)
                restartHomeActivity()
            }
        } else {
            languageIndex++

            CoroutineScope(IO).launch {
                prefs.saveLanguage(languageIndex)
                restartHomeActivity()
            }
        }

    }

    private fun restartHomeActivity() {
        val intent = Intent(this.requireContext(), HomeActivity::class.java)
        intent.putExtra("isRestarted", true)
        startActivity(intent)
    }

    private fun logout() {
        mAuth.signOut()
        activity?.finish()
        startActivity(Intent(activity, SplashScreenActivity::class.java))
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}