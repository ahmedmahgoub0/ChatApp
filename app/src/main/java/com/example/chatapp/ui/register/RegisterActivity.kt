package com.example.chatapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.common.showMessage
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel

        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) { message ->
            showMessage(message)
        }
        viewModel.eventLiveData
            .observe(this, ::handleEvents)
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateToLogin()
        return true
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleEvents(viewEvent: RegisterViewEvent?) {
        when (viewEvent) {
            RegisterViewEvent.NavigateToHome -> {
                navigateToHome()
            }

            RegisterViewEvent.NavigateToLogin -> {
                navigateToLogin()
            }

            else -> {}
        }
    }

}

