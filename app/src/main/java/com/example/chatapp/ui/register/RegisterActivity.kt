package com.example.chatapp.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.model.Message
import com.example.chatapp.ui.common.showLoadingProgressDialog
import com.example.chatapp.ui.common.showMessage
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private var viewBinding: ActivityRegisterBinding? = null
    private lateinit var viewModel: RegisterViewModel
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewBinding!!.lifecycleOwner = this
        viewBinding!!.vm = viewModel
        // activate arrow back in action bar
        setSupportActionBar(viewBinding!!.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) { message -> showMessage(message) }
        viewModel.createUserButton.observe(this) { enable ->
            viewBinding!!.content.createAccountBtn.isEnabled = enable
        }
        viewModel.loadingLiveData.observe(this, ::handleLoadingDialog)
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

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateToLogin()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}

