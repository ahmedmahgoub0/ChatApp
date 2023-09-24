package com.example.chatapp.model

data class Room(
    val id: String? = null,
    val roomName: String? = null,
    val roomDesc: String? = null,
    val ownerId: String? = null,
    val categoryId: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "rooms"
    }
}
