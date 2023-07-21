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
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var isLoading = false
    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        recyclerView = view.findViewById(R.id.music_recyclerview)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        val serverUrl = getString(R.string.server_url) + ":3000"
        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= lastVisibleItem + 2) {
                    loadMore(apiService, searchView.query.toString())
                    isLoading = true
                }
            }
        })

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                page = 0
                loadMore(apiService,query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return view
    }

    // 크롤링 하라고 요청을 보내는 함수
    private fun loadMore(apiService: ApiService, query: String) {
        page++
        val request = CrawlRequest(query, page)

        if(page == 1) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.crawling(request)
                    musicAdapter = MusicAdapter(response.toMutableList())
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter = musicAdapter
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.d("error", "Error: ${e.message}")
                    }
                }
            }
        }
        else {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.crawling(request)
                    withContext(Dispatchers.Main) {
                        musicAdapter.addData(response)
                        musicAdapter.notifyDataSetChanged()
                        isLoading = false
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.d("error", "Error: ${e.message}")
                    }
                }
            }
        }
    }
}
