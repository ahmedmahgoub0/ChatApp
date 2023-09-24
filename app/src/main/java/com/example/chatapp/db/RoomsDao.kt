package com.example.chatapp.db

import com.example.chatapp.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object RoomsDao {

    private fun getCollectionRef(): CollectionReference {
        return Firebase.firestore.collection(Room.COLLECTION_NAME)
    }

    fun createRoom(
        roomName: String,
        roomDesc: String,
        ownerId: String,
        categoryId: String,
        onCompleteListener: OnCompleteListener<Void>
    ) {
        val docRef = getCollectionRef().document()
        val room = Room(
            id = docRef.id,
            roomName = roomName,
            roomDesc = roomDesc,
            ownerId = ownerId,
            categoryId = categoryId
        )
        docRef.set(room)
            .addOnCompleteListener(onCompleteListener)
    }

    fun getRoomsList(onCompleteListener: OnCompleteListener<QuerySnapshot>) {
        getCollectionRef()
            .get()
            .addOnCompleteListener(onCompleteListener)
    }

}