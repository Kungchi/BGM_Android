package com.daeguuniv.bgm_android

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {
    private lateinit var socket: Socket
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<Button>(R.id.btnSend)

        messagesAdapter = MessagesAdapter()
        rvMessages.adapter = messagesAdapter
        rvMessages.layoutManager = LinearLayoutManager(this)

        // Initialize the Socket.IO client
        val opts = IO.Options()
        opts.forceNew = true
        opts.reconnection = true
        socket = IO.socket("http://43.201.248.9:5000", opts)

        // Connect to the server
        socket.connect()

        // Get the room name from the intent's extras
        val roomName = intent.getStringExtra("roomName") ?: "Default Room"

        // Get the username from SharedPreferences
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("Username", "Default User") ?: "Default User"

        // Join the chat room
        socket.emit("join", JSONObject().put("userName", username).put("room", roomName))

        // Listen for new messages
        socket.on("message") { args ->
            val data = args[0] as JSONObject
            val messageContent = data.getString("msg")
            // Create a Message object
            val message = Message(content = messageContent, sender = "", timestamp = System.currentTimeMillis())
            runOnUiThread {
                // Add the message to the adapter and scroll to the bottom
                messagesAdapter.addMessage(message)
                rvMessages.scrollToPosition(messagesAdapter.itemCount - 1)
            }
        }

        // Send a message when the Send button is clicked
        btnSend.setOnClickListener {
            val message = etMessage.text.toString()
            etMessage.text.clear()

            val fullMessage = "$username: $message"
            socket.emit("message", JSONObject().put("msg", fullMessage).put("room", roomName))
        }

        // Initialize MediaPlayer and start buffering audio in the background
        mediaPlayer = MediaPlayer().apply {
            setDataSource("http://43.201.248.9:8000/Dance.mp3") // replace with your audio file url
            setOnPreparedListener { start() }
            prepareAsync() // start buffering in the background
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Disconnect from the server when the activity is destroyed
        socket.disconnect()

        // Release MediaPlayer when the activity is destroyed
        mediaPlayer.release()
    }
}
