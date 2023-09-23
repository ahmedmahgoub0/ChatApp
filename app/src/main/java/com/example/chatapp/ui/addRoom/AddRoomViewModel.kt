package com.example.chatapp.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Category
import com.example.chatapp.model.Message
import com.example.chatapp.ui.common.SingleLiveEvent

class AddRoomViewModel : ViewModel() {

    val eventLiveData = SingleLiveEvent<AddRoomEvent>()
    val messageLiveData = SingleLiveEvent<Message>()
    val loadingLiveData = SingleLiveEvent<Message?>()

    val roomName = MutableLiveData<String>()
    val roomDescription = MutableLiveData<String>()
    val roomNameError = MutableLiveData<String?>()

    val categoriesList = Category.getCategoriesList()

    fun createRoom() {
        if (!validForm()) return


    }

    private fun validForm(): Boolean {
        var isValid = true
        if (roomName.value.isNullOrBlank()) {
            roomNameError.postValue("Can't be empty")
            isValid = false
        } else {
            roomNameError.postValue(null)
        }
        return isValid
    }

}