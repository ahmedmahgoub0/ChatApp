package com.example.chatapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.db.MessagesDao
import com.example.chatapp.model.ChatMessage
import com.example.chatapp.model.Room
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.google.firebase.firestore.DocumentChange

class ChatViewModel : ViewModel() {

    private var room: Room? = null
    val messageLiveData = MutableLiveData<String>()
    val newMessagesLiveData = SingleLiveEvent<List<ChatMessage>>()
    val toastLiveData = SingleLiveEvent<String>()

    fun sendMessage() {
        if (messageLiveData.value.isNullOrBlank()) return
        val message = ChatMessage(
            content = messageLiveData.value,
            senderId = SessionProvider.user?.id,
            senderName = SessionProvider.user?.userName,
            roomId = room?.id
        )
        MessagesDao.sendMessage(message) { task ->
            if (task.isSuccessful) {
                messageLiveData.value = ""
                return@sendMessage
            }
            toastLiveData.postValue("something went wrong")
        }
    }

    fun changeRoom(room: Room?) {
        this.room = room
        listenForMessagesInRoom()
    }

    private fun listenForMessagesInRoom() {
        MessagesDao
            .getMessagesCollection(room?.id ?: "")
            .orderBy("time")
            .limitToLast(100)
            .addSnapshotListener { snapShot, error ->
                val newMessages = mutableListOf<ChatMessage>()
                snapShot?.documentChanges?.forEach { docChange ->
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val message = docChange.document.toObject(ChatMessage::class.java)
                        newMessages.add(message)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                    }
                }
                newMessagesLiveData.postValue(newMessages)
            }
    }
}