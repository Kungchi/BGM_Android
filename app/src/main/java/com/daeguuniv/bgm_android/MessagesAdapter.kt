package com.daeguuniv.bgm_android

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Message(val content: String, val sender: String, val timestamp: Long)

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val messages: MutableList<Message> = mutableListOf()

    class MessageViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val textView = TextView(parent.context)
        return MessageViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.textView.text = "${message.sender}: ${message.content}"
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }
}