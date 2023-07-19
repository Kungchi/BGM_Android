package com.daeguuniv.bgm_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        recyclerView = view.findViewById(R.id.music_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val serverUrl = getString(R.string.server_url) + ":3000"
        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl) // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val request = CrawlRequest(query, 1)

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiService.crawling(request)
                        musicAdapter = MusicAdapter(response)
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter = musicAdapter
                        }
                    }
                    catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.d("error", "Error: ${e.message}")
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // 검색어가 바뀔 때마다 호출됩니다
                // 필요한 경우 이를 활용하여 실시간 검색 기능을 구현할 수 있습니다
                return true
            }
        })

        return view
    }
}