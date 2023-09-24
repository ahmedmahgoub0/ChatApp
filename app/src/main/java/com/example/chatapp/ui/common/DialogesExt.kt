package com.example.chatapp.ui.common

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.example.chatapp.model.Message

fun Activity.showMessage(message: Message): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message.message)
    if (message.posActionName != null) {
        dialogBuilder.setPositiveButton(
            message.posActionName,
            message.posAction
        )
    }
    if (message.negActionName != null) {
        dialogBuilder.setNegativeButton(
            message.negActionName,
            message.negAction
        )
    }
    dialogBuilder.setCancelable(message.isCancellable)
    return dialogBuilder.show()
}

fun Fragment.showMessage(message: Message): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setMessage(message.message)
    if (message.posActionName != null) {
        dialogBuilder.setPositiveButton(
            message.posActionName,
            message.posAction
        )
    }
    if (message.negActionName != null) {
        dialogBuilder.setNegativeButton(
            message.negActionName,
            message.negAction
        )
    }
    dialogBuilder.setCancelable(message.isCancellable)
    return dialogBuilder.show()
}

fun Activity.showLoadingProgressDialog(
    message: String,
    isCancellable: Boolean = true
): AlertDialog {
    val alertDialog = ProgressDialog(this)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(isCancellable)
    return alertDialog
}