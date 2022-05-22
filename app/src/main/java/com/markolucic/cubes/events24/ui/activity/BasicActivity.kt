package com.markolucic.cubes.events24.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.tools.LanguageTools
import com.markolucic.cubes.events24.ui.view.CustomToast

open class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        CustomToast.showMessage(this, "LANGUAGE")


//        DataStorePrefs(this).getLanguage().asLiveData().observe(this) {
//
//            when (it) {
//                0 -> LanguageTools.setLanguageResourceConfiguration("en", this)
//                else -> LanguageTools.setLanguageResourceConfiguration("sr", this)
//            }
//
//            when (it) {
//                0 -> LanguageTools.setLanguage("en", applicationContext)
//                else -> LanguageTools.setLanguage("sr", applicationContext)
//            }
//
//            CustomToast.showMessage(this, "LANGUAGE: $it")
//        }

        LanguageTools.setAppLanguage(this)

    }



}