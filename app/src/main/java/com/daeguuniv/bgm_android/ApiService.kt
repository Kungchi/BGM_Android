package com.daeguuniv.bgm_android

import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(
    val Username: String,
    val Password: String
)

data class LoginResponse(
    val message: String
)

data class Music(
    val rank: Int,
    val imgurl: String,
    val title: String,
    val singer: String
)

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: LoginRequest): LoginResponse

    @GET("music")
    suspend fun getMusicList(): List<Music>
}
