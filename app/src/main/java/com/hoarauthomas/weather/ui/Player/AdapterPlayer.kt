package com.hoarauthomas.weather.ui.Player

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.hoarauthomas.weather.databinding.ItemPlayerBinding

class AdapterPlayer(
    private val medias: MutableList<String>,
    private val requireContext1: Context
) :
    RecyclerView.Adapter<AdapterPlayer.ViewHolder>() {

    class ViewHolder(binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerView = binding.playerView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPlayer.ViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterPlayer.ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AdapterPlayer.ViewHolder, position: Int) {
        val simplePlayer = ExoPlayer.Builder(requireContext1).build()

        holder.playerView.player = simplePlayer
        holder.playerView.tag = position

        val mediaItem = MediaItem.fromUri(medias[position])

        simplePlayer.setMediaItem(mediaItem)
        simplePlayer.prepare()
        //play
        simplePlayer.play()

    }

    override fun getItemCount(): Int {
        return medias.size
    }


}