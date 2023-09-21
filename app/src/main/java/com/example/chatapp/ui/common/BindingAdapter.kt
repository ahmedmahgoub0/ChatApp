package com.example.chatapp.ui.common

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun bindErrorOnTextInputLayout(
    textInputLayout: TextInputLayout,
    message: String?
) {
    textInputLayout.error = message
}