import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeguuniv.bgm_android.ApiService
import com.daeguuniv.bgm_android.Music
import com.daeguuniv.bgm_android.MusicAdapter
import com.daeguuniv.bgm_android.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

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

        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl) // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val musicList = apiService.getMusicList()
            musicAdapter = MusicAdapter(musicList)
            withContext(Dispatchers.Main) {
                recyclerView.adapter = musicAdapter
            }
        }

        return view
    }
}
