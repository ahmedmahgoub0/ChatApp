package com.example.chatapp.ui.fragments.roomsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.db.RoomsDao
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.example.chatapp.ui.common.SingleLiveEvent

class RoomsViewModel : ViewModel() {

    val messageLiveData = SingleLiveEvent<Message>()
    // val noRoomsYet = MutableLiveData(true)

    val roomsLiveData = MutableLiveData<List<Room>>()

    private var rooms = mutableListOf<Room>()
    fun loadRooms() {
        getUserRooms()
        // room must has users id to get user rooms
//        if (rooms.size == 0)
//            return
//        else {
//            rooms.forEach() { room ->
//                if (room.ownerId != SessionProvider.user?.id)
//                    rooms.remove(room)
//            }
//        }
    }

    private fun getUserRooms() {
        RoomsDao
            .getRoomsList() { task ->
                if (!task.isSuccessful) {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage ?: "",
                            posActionName = "ok",
                            posAction = { dialogInterface, i ->
                                dialogInterface.dismiss()
                            }
                        )
                    )
                } else {
                    rooms = task.result.toObjects(Room::class.java)
                    roomsLiveData.postValue(rooms)
                }
            }
    }


}