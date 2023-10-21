package com.example.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: String? = null,
    val roomName: String? = null,
    val roomDesc: String? = null,
    val ownerId: String? = null,
    val categoryId: String? = null,
    val usersId: List<String?>? = null
) : Parcelable {
    companion object {
        const val ROOM_KEY = "room"
        const val COLLECTION_NAME = "rooms"
    }
}
