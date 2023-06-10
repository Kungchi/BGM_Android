package com.daeguuniv.bgm_android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val Username: String,
    val Password: String
)

data class LoginResponse(
    val message: String
)

interface LoginService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

