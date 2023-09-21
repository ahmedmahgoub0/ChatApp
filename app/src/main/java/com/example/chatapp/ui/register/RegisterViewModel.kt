package com.example.chatapp.ui.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.db.UsersDao
import com.example.chatapp.model.User
import com.example.chatapp.ui.Message
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    val messageLiveData = SingleLiveEvent<Message>()
    val eventLiveData = SingleLiveEvent<RegisterViewEvent>()

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirm = MutableLiveData<String>()

    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val passwordConfirmError = MutableLiveData<String?>()

    private var auth = Firebase.auth
    private var firestore = Firebase.firestore

    fun navigateToLogin() {
        eventLiveData.postValue(RegisterViewEvent.NavigateToLogin)
    }

    fun register() {
        if (!validForm()) return
        auth.createUserWithEmailAndPassword(
            email.value!!,
            password.value!!
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                insertUserToFirestore(task.result.user?.uid)
            } else {
                messageLiveData.postValue(
                    Message(
                        message = task.exception?.localizedMessage,
                        posActionName = "ok",
                        posAction = { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                    )
                )
            }
        }
    }

    private fun insertUserToFirestore(uid: String?) {
        val user = User(
            id = uid,
            userName = userName.value,
            email = email.value
        )
        UsersDao
            .createUser(user) { task ->
                if (task.isSuccessful) {
                    messageLiveData.postValue(
                        Message(
                            message = "Registered successfully",
                            posActionName = "ok",
                            posAction = { dialogInterface, i ->
                                SessionProvider.user = user
                                eventLiveData.postValue(RegisterViewEvent.NavigateToHome)
                            }
                        )
                    )
                } else {
                    messageLiveData.postValue(
                        Message(
                            message = task.exception?.localizedMessage,
                        )
                    )
                }
            }
    }

    private fun validForm(): Boolean {
        var isValid = true
        if (userName.value.isNullOrBlank()) {
            userNameError.postValue("Can't be empty")
            isValid = false
        } else {
            userNameError.postValue(null)
        }
        if (email.value.isNullOrBlank()) {
            emailError.postValue("Can't be empty")
            isValid = false
        } else {
            emailError.postValue(null)
        }
        if (password.value.isNullOrBlank()) {
            passwordError.postValue("Can't be empty")
            isValid = false
        } else {
            passwordError.postValue(null)
        }
        if (passwordConfirm.value.isNullOrBlank()) {
            passwordConfirmError.postValue("Can't be empty")
            isValid = false
        } else {
            if (passwordConfirm.value != password.value) {
                passwordConfirmError.postValue("Doesn't match")
                isValid = false
            } else {
                passwordConfirmError.postValue(null)
            }
        }
        return isValid
    }
}
