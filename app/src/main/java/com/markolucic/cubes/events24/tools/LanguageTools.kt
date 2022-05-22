package com.markolucic.cubes.events24.tools

import android.app.Activity
import android.content.Context
import androidx.lifecycle.asLiveData
import com.markolucic.cubes.events24.data.datastore.DataStorePrefs
import com.markolucic.cubes.events24.ui.activity.BasicActivity
import java.util.*

class LanguageTools {

    companion object {

//        fun setLanguageResourceConfiguration(languageCode: String, activity: Activity) {
//
//            val resources = activity.resources
//
//            val configuration = resources.configuration
//
//            val displayMetrics = resources.displayMetrics
//
//            configuration?.setLocale(Locale(languageCode.lowercase()))
//
//            resources.updateConfiguration(configuration, displayMetrics)
//        }
//
//        fun setLanguage(languageCode: String, context: Context) {
//            val locale = Locale(languageCode)
//            Locale.setDefault(locale)
//            val resources = context.resources
//            val configuration = resources.configuration
//            configuration.setLocale(locale)
//            resources.updateConfiguration(configuration, resources.displayMetrics)
//        }

        fun setAppLanguage(basicActivity: BasicActivity) {

            DataStorePrefs(basicActivity).getLanguage().asLiveData().observe(basicActivity) {

                val language = when(it){
                    0 -> "en"
                    else -> "sr"
                }

                val locale = Locale(language)
                Locale.setDefault(locale)
                val resources = basicActivity.resources
                val configuration = resources.configuration
                configuration.setLocale(locale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
            }
        }
    }

}