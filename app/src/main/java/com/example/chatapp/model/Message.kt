package com.example.chatapp.model

import android.content.DialogInterface

data class Message(
    val message: String? = null,
    val posActionName: String? = null,
    val posAction: DialogInterface.OnClickListener? = null,
    val negActionName: String? = null,
    val negAction: DialogInterface.OnClickListener? = null,
    val isCancellable: Boolean = true
)