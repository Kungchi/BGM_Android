package com.daeguuniv.bgm_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MusicAdapter(private var musicList: MutableList<Music>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewTitle)
        val singer: TextView = view.findViewById(R.id.textViewSinger)
        val image: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentItem = musicList[position]
        holder.title.text = currentItem.title
        holder.singer.text = currentItem.singer
        // Use a library like Glide or Picasso to load the image from the URL
        Glide.with(holder.image.context).load(currentItem.imgurl).into(holder.image)
    }

    override fun getItemCount() = musicList.size

    fun addData(newData: List<Music>) {
        musicList.addAll(newData)
    }
}

