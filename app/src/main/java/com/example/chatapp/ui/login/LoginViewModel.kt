package com.example.chatapp.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.db.UsersDao
import com.example.chatapp.model.Message
import com.example.chatapp.model.User
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val messageLiveData = SingleLiveEvent<Message>()
    val eventLiveData = SingleLiveEvent<LoginViewEvent>()
    val loadingLiveEvent = SingleLiveEvent<Message?>()

    val email = MutableLiveData<String>("ahmed1@route.com")
    val password = MutableLiveData<String>("123456")
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    private var auth = Firebase.auth

    fun navigateToLogin() {
        eventLiveData.postValue(LoginViewEvent.NavigateToRegister)
    }

    fun login() {
        if (!validForm()) return
        loadingLiveEvent.postValue(
            Message(
                message = "loading...",
                isCancellable = false
            )
        )
        auth.signInWithEmailAndPassword(
            email.value!!,
            password.value!!
        ).addOnCompleteListener { task ->
            loadingLiveEvent.postValue(null)
            if (task.isSuccessful) {
                Log.d(TAG, "SignInWithEmail:success")
                getUserFromFirestore(task.result.user?.uid)
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

    private fun getUserFromFirestore(uid: String?) {
        UsersDao
            .getUser(uid) { task ->
                val user = task.result.toObject(User::class.java)
                SessionProvider.user = user
                messageLiveData.postValue(
                    Message(
                        message = "Logged in successfully",
                        posActionName = "ok",
                        posAction = { dialogInterface, i ->
                            eventLiveData.postValue(LoginViewEvent.NavigateToHome)
                        }
                    )
                )
            }
    }

    private fun validForm(): Boolean {
        var isValid = true
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
        return isValid
    }
}
