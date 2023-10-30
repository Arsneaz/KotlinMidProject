package com.example.kotlinmidproject

import UserPreferencesManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmidproject.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UserPreferencesManager.initialize(this)
        val userPreferences = UserPreferencesManager.getUserPreferences()


        binding.btnLogin.setOnClickListener {
        val inputUsername = binding.usernameText.text.toString()
        val inputPassword = binding.passwordText.text.toString()

        lifecycleScope.launch {
            val userData = userPreferences.getUserData(inputUsername)
            Log.d(inputUsername, "input username")
            Log.d(inputPassword, "pass")
            Log.d(userData.username.toString(), "datastore username")
            if (inputUsername == userData.username && inputPassword == userData.password) {
                userPreferences.setLoggedIn(true)
                userPreferences.setUsername(inputUsername)
                Log.d(userPreferences.getStatus().asLiveData().toString(), "check status")
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT)
                    .show()

            }
        }

    }
        binding.btnRegister.setOnClickListener {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    }
}