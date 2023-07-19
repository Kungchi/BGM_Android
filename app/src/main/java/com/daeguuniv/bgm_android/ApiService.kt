package com.daeguuniv.bgm_android
import retrofit2.http.*

data class LoginRequest(
    val Username: String,
    val Password: String
)

data class LoginResponse(
    val message: String,
    val token: String // Add this
)

data class CrawlRequest(
    val keyword: String,
    val page: Int
)
data class Music(
    val rank: Int? = null,
    val imgurl: String,
    val title: String,
    val singer: String
)

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @POST("register")
    suspend fun register(@Body request: LoginRequest): LoginResponse
    @POST("crawl")
    suspend fun crawling(@Body request: CrawlRequest): List<Music>
    @GET("Merge")
    suspend fun getMusicList(): List<Music>
    @GET("PlayList")
    suspend fun getPlayList() : List<Music>
}
