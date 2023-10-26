package com.example.kotlinmidproject.data

import android.content.Context
import android.service.autofill.UserData
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.*
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastoretest.data.local.IPreferenceDataStoreAPI
import com.example.kotlinmidproject.data.PreferenceDataStoreConstants.IS_LOGGED_IN
import com.example.kotlinmidproject.data.PreferenceDataStoreConstants.PASSWORD_KEY
import com.example.kotlinmidproject.data.PreferenceDataStoreConstants.USERNAME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// Need to make an helper model to store both methods and key
// https://proandroiddev.com/preference-datastore-the-generic-way-d26b11f1075f


// Wtf when I put this inside the class it broken, like wtf I spend 2 hour solving this. Or does when every instance that got called will make a new DataStore?
// For Splash, Login and Register. I'm calling UserPreferences via UserPrerencesManager, but why this is working????
// If i make the instance on UserPreferences directly. it will be fine??
// It's not working ayaya
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferences(context: Context) {

    private val dataSource = context.datastore

    // I guess there's some way to use NIK as the key for the list. But I guess i'm to lazy to do that lmao
    companion object{
//        val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
//        val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
//        val GITHUB_KEY = stringPreferencesKey("GITHUB_KEY")
//        val NIK_KEY = stringPreferencesKey("NIK_KEY")
//        val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    }

    suspend fun setLoggedIn(isLoggedIn : Boolean) {
        dataSource.edit {pref ->
            pref[IS_LOGGED_IN] = isLoggedIn
        }
    }

    fun getStatus() : Flow<Boolean> {
        return dataSource.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {pref ->
            val loggedStatus = pref[IS_LOGGED_IN] ?: false
            loggedStatus
        }
    }
//    suspend fun saveUserCredentials(username: String, password: String, github: String, nik: String, email: String) {
//        dataSource.edit {pref ->
//            pref[USERNAME_KEY] = username
//            pref[PASSWORD_KEY] = password
//            pref[GITHUB_KEY] = github
//            pref[NIK_KEY] = email
//            pref[EMAIL_KEY] = nik
//        }
//    }

    private fun getUsernameKey(username: String): Preferences.Key<String> {
        return stringPreferencesKey("username_$username")
    }

    private fun getPasswordKey(username: String): Preferences.Key<String> {
        return stringPreferencesKey("password_$username")
    }

    private fun getGithubKey(username: String): Preferences.Key<String> {
        return stringPreferencesKey("github_$username")
    }

    private fun getNikKey(username: String): Preferences.Key<String> {
        return stringPreferencesKey("NIK_$username")
    }

    private fun getEmailKey(username: String): Preferences.Key<String>{
        return stringPreferencesKey("email_$username")
    }

    suspend fun saveUserCredentials(username: String, password: String, github: String, nik: String, email: String) {
        dataSource.edit {pref ->
            pref[getUsernameKey(username)] = username
            pref[getPasswordKey(username)] = password
            pref[getGithubKey(username)] = github
            pref[getNikKey(username)] = nik
            pref[getEmailKey(username)] = email
        }
    }
    suspend fun getUserData(username: String):  com.example.kotlinmidproject.data.UserData{
        val preferences = dataSource.data.first()
        return UserData(
            preferences[getUsernameKey(username)] ?: "",
            preferences[getPasswordKey(username)] ?: "",
            preferences[getGithubKey(username)] ?: "",
            preferences[getNikKey(username)] ?: "",
            preferences[getEmailKey(username)] ?: ""
        )
    }

// This is a little bit more advanced to lazy me copium
//    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T) :
//            Flow<T> = dataSource.data.catch { exception ->
//        if (exception is IOException){
//            emit(emptyPreferences())
//        }else{
//            throw exception
//        }
//    }.map { preferences->
//        val result = preferences[key]?: defaultValue
//        result
//    }
//
//    override suspend fun <T> getFirstPreference(key: Key<T>, defaultValue: T) :
//        T = dataSource.data.first()[key] ?: defaultValue
//
//    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
//        dataSource.edit { preferences ->
//            preferences[key] = value
//        }
//    }
//
//    override suspend fun <T> removePreference(key: Key<T>) {
//        dataSource.edit { preferences ->
//            preferences.remove(key)
//        }
//    }
//
//    override suspend fun <T> clearAllPreference() {
//        dataSource.edit { preferences ->
//            preferences.clear()
//        }
//    }
}
