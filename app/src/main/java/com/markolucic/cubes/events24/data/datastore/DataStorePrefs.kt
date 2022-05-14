package com.markolucic.cubes.events24.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


const val PREFERENCES_NAME = "events_preferences"

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStorePrefs(val context: Context) {

    companion object {
        val EMAIL = stringPreferencesKey("prefs_email")
        val LANGUAGE = intPreferencesKey("prefs_language")
        val NOTIFICATIONS = booleanPreferencesKey("prefs_notifications")
    }


    suspend fun saveEmail(email: String) {
        context.datastore.edit {
            it[EMAIL] = email
        }
    }

    fun getEmail() = context.datastore.data.map {
        it[EMAIL] ?: ""
    }

    suspend fun saveLanguage(languageResource: Int) {
        context.datastore.edit {
            it[LANGUAGE] = languageResource
        }
    }

    fun getLanguage() = context.datastore.data.map {
        it[LANGUAGE] ?: 0
    }

    suspend fun setNotificationStatus(isOn: Boolean) {
        context.datastore.edit {
            it[NOTIFICATIONS] = isOn
        }
    }

    fun isNotificationOn() = context.datastore.data.map { it[NOTIFICATIONS] ?: true }

}
