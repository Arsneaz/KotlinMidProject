package com.example.kotlinmidproject.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreConstants {
    val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
    val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
    val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
}