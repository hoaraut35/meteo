package com.hoarauthomas.weather.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor()  {

    private val chatCollection : String  = "chats"
    private val messageCollection : String = "messages"

    private fun getChatCollection() : CollectionReference{
        return FirebaseFirestore.getInstance().collection(chatCollection)
    }

    fun getAllMessageForChat(chat : String):Query{
        return this.getChatCollection()
            .document(chat)
            .collection(messageCollection)
            .orderBy("dateCreated")
            .limit(50)
    }


}