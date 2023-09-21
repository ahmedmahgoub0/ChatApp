package com.example.chatapp.db

import com.example.chatapp.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UsersDao {

    private fun getUserCollection(): CollectionReference {
        val firestore = Firebase.firestore
        return firestore.collection(DatabaseConstants.USERS_COLLECTION_PATH)
    }

    fun createUser(user: User, onCompleteListener: OnCompleteListener<Void>) {
        getUserCollection()
            .document(user.id!!)
            .set(user)
            .addOnCompleteListener(onCompleteListener)
    }

    fun getUser(uid: String? = null, onCompleteListener: OnCompleteListener<DocumentSnapshot>) {
        getUserCollection()
            .document(uid!!)
            .get()
            .addOnCompleteListener(onCompleteListener)
    }
}