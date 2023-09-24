package com.example.chatapp.ui.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.model.Message
import com.example.chatapp.ui.common.showLoadingProgressDialog
import com.example.chatapp.ui.common.showMessage
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var loadingDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel

    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) { message -> showMessage(message) }
        viewModel.loginButton.observe(this) { enable ->
            viewBinding.content.loginBtn.isEnabled = enable
        }
        viewModel.loadingLiveEvent.observe(this, ::handleLoadingDialog)
        viewModel.eventLiveData.observe(this, ::handleEvents)
    }

    private fun handleLoadingDialog(message: Message?) {
        if (message == null) {
            // hide
            loadingDialog?.dismiss()
            loadingDialog = null
            return
        }
        // show
        loadingDialog = showLoadingProgressDialog(
            message = message.message ?: "",
            isCancellable = message.isCancellable
        )
        loadingDialog!!.show()
    }

    private fun handleEvents(viewEvent: LoginViewEvent?) {
        when (viewEvent) {
            LoginViewEvent.NavigateToHome -> {
                navigateToHome()
            }

            LoginViewEvent.NavigateToRegister -> {
                navigateToRegister()
            }

            else -> {}
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

}