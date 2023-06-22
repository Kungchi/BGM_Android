package com.daeguuniv.bgm_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.resgisterButton)

        val serverUrl = getString(R.string.server_url)

        if (serverUrl != null) {
            try {
                Log.d("LoginActivity", "Initializing Retrofit with server URL: $serverUrl")
                val retrofit = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                apiService = retrofit.create(ApiService::class.java)

                Log.d("LoginActivity", "Retrofit initialized successfully")
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error occurred during Retrofit initialization: ", e)
                throw e
            }
        } else {
            Log.e("LoginActivity", "Server url is null.")
        }


        loginButton.setOnClickListener {
            performLogin()
        }

        registerButton.setOnClickListener {
            performRegistration()
        }
    }

    private fun performLogin() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val request = LoginRequest(username, password)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.login(request)
                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            // Save the JWT to SharedPreferences
                            val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("JWT", response.token)
                            editor.apply()

                            Toast.makeText(
                                this@LoginActivity,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performRegistration() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val request = LoginRequest(username, password)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.register(request)
                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            Toast.makeText(
                                this@LoginActivity,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this@LoginActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.d("error", "${e.message}")
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_SHORT).show()
        }
    }
}
