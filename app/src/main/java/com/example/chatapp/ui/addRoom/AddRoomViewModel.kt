package com.example.chatapp.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.db.RoomsDao
import com.example.chatapp.model.Category
import com.example.chatapp.model.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent

class AddRoomViewModel : ViewModel() {

    val eventLiveData = SingleLiveEvent<AddRoomEvent>()
    val messageLiveData = SingleLiveEvent<Message>()
    val loadingLiveData = SingleLiveEvent<Message?>()

    val roomName = MutableLiveData<String>()
    val roomDescription = MutableLiveData<String>()
    val roomNameError = MutableLiveData<String?>()

    val categoriesList = Category.getCategoriesList()
    private var selectedCategory = categoriesList[0]

    fun createRoom() {
        if (!validForm()) return
        // loading progress dialog activation = on
        loadingLiveData.postValue(
            Message(
                message = "loading...",
                isCancellable = false
            )
        )
        RoomsDao
            .createRoom(
                roomName = roomName.value!!,
                roomDesc = roomDescription.value ?: "",
                ownerId = SessionProvider.user?.id!!,
                categoryId = selectedCategory.id!!
            ) { task ->
                // loading progress dialog activation = off
                loadingLiveData.postValue(null)
                if (task.isSuccessful) {
                    messageLiveData.postValue(
                        Message(
                            message = "Room created successfully",
                            posActionName = "ok",
                            posAction = { dialogInterface, i ->
                                eventLiveData.postValue(AddRoomEvent.NavigateToHome)
                            }
                        )
                    )
                } else {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage ?: "something went wrong",
                            posActionName = "ok",
                            posAction = { dialogInterface, i ->
                                dialogInterface.dismiss()
                            }
                        )
                    )
                }

            }
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

    fun onItemSelected(position: Int) {
        selectedCategory = categoriesList[position]
    }

}