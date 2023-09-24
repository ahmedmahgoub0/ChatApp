package com.example.chatapp.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chatapp.db.UsersDao
import com.example.chatapp.model.User
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel : ViewModel() {

    val eventLiveData = SingleLiveEvent<SplashViewEvent>()
    fun redirect() {
        if (Firebase.auth.currentUser == null) {
            eventLiveData.postValue(SplashViewEvent.NavigateToLogin)
            return
        }
        UsersDao.getUser(
            Firebase.auth.currentUser!!.uid ?: ""
        ) { task ->
            if (!task.isSuccessful) {
                eventLiveData.postValue(SplashViewEvent.NavigateToLogin)
                return@getUser
            }
            val user = task.result.toObject(User::class.java)
            SessionProvider.user = user
            eventLiveData.postValue(SplashViewEvent.NavigateToHome)
        }


    }
}