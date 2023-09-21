package com.example.chatapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.ui.common.showMessage
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

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
        viewModel.messageLiveData
            .observe(this) { message ->
                showMessage(message)
            }
        viewModel.eventLiveData
            .observe(this, ::handleEvents)
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