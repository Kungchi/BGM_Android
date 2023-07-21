package com.daeguuniv.bgm_android

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayListFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.music_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val serverUrl = getString(R.string.server_url) + ":3000"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                val jwtToken = getJWTToken()
                if (jwtToken != null) {
                    val builder = originalRequest.newBuilder().header("Authorization", "Bearer " + jwtToken)
                    val newRequest = builder.build()
                    chain.proceed(newRequest)
                } else {
                    chain.proceed(originalRequest)
                }
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val musicList = apiService.getPlayList()
            musicAdapter = MusicAdapter(musicList.toMutableList())
            withContext(Dispatchers.Main) {
                recyclerView.adapter = musicAdapter
            }
        }

        return view
    }

    private fun getJWTToken(): String? {
        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("JWT", null)
    }
}
