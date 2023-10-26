package com.example.kotlinmidproject
import UserPreferencesManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmidproject.data.PreferenceDataStoreConstants.USERNAME_KEY
import com.example.kotlinmidproject.data.UserPreferences
import com.example.kotlinmidproject.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
//        UserPreferencesManager.initialize(this)
//        val userPreferences = UserPreferencesManager.getUserPreferences()
        userPreferences = UserPreferences(this)

        setContentView(binding.root)

        // Delay for 3 seconds and then start the main activity
        Handler().postDelayed({
            userPreferences.getStatus().asLiveData().observe(this ) {status ->
                if (!status) {
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 3000)
    }
}
