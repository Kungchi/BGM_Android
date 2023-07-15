package com.daeguuniv.bgm_android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class ChatroomFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chatroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBallade = view.findViewById<Button>(R.id.btn_Ballade)
        btnBallade.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Ballade") // Add the room name to the intent's extras
            startActivity(intent)
        }


        val btnDance = view.findViewById<Button>(R.id.btn_Dance)
        btnDance.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Dance") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnFolk = view.findViewById<Button>(R.id.btn_Folk)
        btnFolk.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Folk") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnHiphop = view.findViewById<Button>(R.id.btn_HipHop)
        btnHiphop.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "HipHop") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnIndie = view.findViewById<Button>(R.id.btn_Indie)
        btnIndie.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Indie") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnRB = view.findViewById<Button>(R.id.btn_RB)
        btnRB.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "RB") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnRock = view.findViewById<Button>(R.id.btn_Rock)
        btnRock.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Rock") // Add the room name to the intent's extras
            startActivity(intent)
        }

        val btnTrot = view.findViewById<Button>(R.id.btn_Trot)
        btnTrot.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("roomName", "Trot") // Add the room name to the intent's extras
            startActivity(intent)
        }

    }
}
