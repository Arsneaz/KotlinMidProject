package com.example.kotlinmidproject

import UserPreferencesManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmidproject.data.UserPreferences
import com.example.kotlinmidproject.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setting for the catch
        UserPreferencesManager.initialize(this)
        val userPreferences = UserPreferencesManager.getUserPreferences()

        // Check the input
        checkInput()

        binding.btnRegister.setOnClickListener{
            val inputUsername = binding.usernameText.text.toString()
            val inputPassword = binding.passwordText.text.toString()
            val inputEmail = binding.emailText.text.toString()
            val inputGithub = binding.githubText.text.toString()
            val inputNik = binding.nikText.text.toString()

            // Is is supposed to be using ViewModel KekW. I'm lmao ayaya...
            lifecycleScope.launch {
                userPreferences.saveUserCredentials(
                    username = inputUsername,
                    password = inputPassword,
                    github = inputGithub,
                    nik = inputNik,
                    email = inputEmail
                )
            }

            // Redirecting to Login Activity
            Toast.makeText(this, "Sucessfully created an accout", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun checkInput(){
        binding.usernameText.doOnTextChanged { text, start, before, count ->
            if (text!!.length <= 5) {
                binding.usernameField.error = "Invalid username (minimum 5 character) "
            } else {
                binding.usernameField.error = null
            }
        }

        binding.passwordText.doOnTextChanged { text, start, before, count ->
            if (text!!.length <= 8) {
                binding.passwordField.error = "Password must contain 8 minimal character"
            } else {
                binding.passwordField.error = null
            }
        }

        binding.nikText.doOnTextChanged { text, start, before, count ->
            if ( text!!.all{char -> char.isDigit()} && text.length == 16) {
                binding.nikField.error = null
            } else {
                binding.nikField.error = "NIK must 16 character of number"
            }
        }

        binding.emailText.doOnTextChanged { text, start, before, count ->
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (text!!.matches(emailPattern.toRegex()) && text.length != 0) {
                binding.emailField.error =  null
            } else {
                binding.emailField.error = "Must be validate E-mail address"
            }
        }
    }
}