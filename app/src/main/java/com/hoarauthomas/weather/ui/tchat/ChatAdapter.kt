package com.hoarauthomas.weather.ui.tchat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.hoarauthomas.weather.databinding.ItemChatBinding
import com.hoarauthomas.weather.models.ChatMessage


class ChatAdapter(options: FirestoreRecyclerOptions<ChatMessage>) :
    FirestoreRecyclerAdapter<ChatMessage, ChatAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ChatMessage) {

        holder.textViewPriority.text = model.message

    }

    override fun onDataChanged() {
        super.onDataChanged()
    }


    inner class ViewHolder(binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        var textViewPriority: TextView = binding.messageText
    }


}