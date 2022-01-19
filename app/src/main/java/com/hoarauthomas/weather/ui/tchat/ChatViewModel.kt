package com.hoarauthomas.weather.ui.tchat

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.hoarauthomas.weather.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository):ViewModel(){

    //get all messages for android chat
    fun getAllMessageForChat(chat : String):Query{
        return chatRepository.getAllMessageForChat(chat)
    }

}