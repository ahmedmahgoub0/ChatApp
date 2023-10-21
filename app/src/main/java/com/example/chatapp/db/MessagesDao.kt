package com.example.chatapp.db

import com.example.chatapp.model.ChatMessage
import com.google.android.gms.tasks.OnCompleteListener

object MessagesDao {

    fun getMessagesCollection(roomId: String) =
        RoomsDao.getCollectionRef()
            .document(roomId)
            .collection(ChatMessage.MESSAGES_COLLECTION)

    fun sendMessage(message: ChatMessage, onCompleteListener: OnCompleteListener<Void>) {
        val messageDoc = getMessagesCollection(message.roomId ?: "")
            .document()
        message.id = messageDoc.id
        messageDoc
            .set(message)
            .addOnCompleteListener(onCompleteListener)
    }

}