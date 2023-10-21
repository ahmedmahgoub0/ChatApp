package com.example.chatapp.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class ChatMessage(
    var id: String? = null,
    val content: String? = null,
    val time: Timestamp = Timestamp.now(),
    val senderId: String? = null,
    val senderName: String? = null,
    val roomId: String? = null
) {
    companion object {
        const val MESSAGES_COLLECTION = "messages"
    }

    fun reformatTime(): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(time.toDate())
    }
}
