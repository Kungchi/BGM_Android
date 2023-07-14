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
            Toast.makeText(requireContext(), "댄스 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnFolk = view.findViewById<Button>(R.id.btn_Folk)
        btnFolk.setOnClickListener {
            Toast.makeText(requireContext(), "포크 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnHiphop = view.findViewById<Button>(R.id.btn_HipHop)
        btnHiphop.setOnClickListener {
            Toast.makeText(requireContext(), "힙합 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnIndie = view.findViewById<Button>(R.id.btn_Indie)
        btnIndie.setOnClickListener {
            Toast.makeText(requireContext(), "인디 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnRB = view.findViewById<Button>(R.id.btn_RB)
        btnRB.setOnClickListener {
            Toast.makeText(requireContext(), "알앤비 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnRock = view.findViewById<Button>(R.id.btn_Rock)
        btnRock.setOnClickListener {
            Toast.makeText(requireContext(), "락 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

        val btnTrot = view.findViewById<Button>(R.id.btn_Trot)
        btnTrot.setOnClickListener {
            Toast.makeText(requireContext(), "트로트 버튼을 눌렀음", Toast.LENGTH_SHORT).show()
        }

    }
}
