package com.example.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.common.SingleLiveEvent

class HomeViewModel : ViewModel() {

    val eventLiveData = SingleLiveEvent<HomeViewEvent>()
//

    fun navigateToAddRoom() {
        eventLiveData.postValue(HomeViewEvent.NavigateToAddRoom)
    }

}