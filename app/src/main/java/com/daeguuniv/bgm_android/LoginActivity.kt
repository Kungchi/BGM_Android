package com.daeguuniv.bgm_android

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        val serverUrl = getString(R.string.server_url)


        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl) // 실제 API의 기본 URL을 입력해야 합니다.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loginService = retrofit.create(LoginService::class.java)

        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            // 로그인 요청에 대한 코드를 작성합니다.
            val request = LoginRequest(username, password)
            loginService.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val statusCode = response.code()
                    Log.d("STATUS_CODE", "Code: $statusCode")
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Toast.makeText(
                            this@LoginActivity,
                            loginResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }


                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d("error", "${t.message}")
                }

            })

        } else {
            Toast.makeText(this, "사용자명과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}
