package com.example.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    val eventLiveData = SingleLiveEvent<HomeViewEvent>()
    val messageLiveData = SingleLiveEvent<Message>()

    fun navigateToAddRoom() {
        eventLiveData.postValue(HomeViewEvent.NavigateToAddRoom)
    }

    fun logout() {
        messageLiveData.postValue(
            Message(
                message = "Are you sure you want to logout?",
                posActionName = "yes",
                posAction = { dialogInterface, i ->
                    Firebase.auth.signOut()
                    SessionProvider.user = null
                    eventLiveData.postValue(HomeViewEvent.NavigateToLogin)
                },
                negActionName = "cancel",
                negAction = { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            )
        )
    }

}