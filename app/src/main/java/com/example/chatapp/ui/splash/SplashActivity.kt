package com.example.chatapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivitySplashBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var viewBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        subscribeToLiveData()
        showSplash()
    }

    private fun subscribeToLiveData() {
        viewModel.eventLiveData.observe(this, ::handleEvents)
    }

    private fun handleEvents(splashViewEvent: SplashViewEvent?) {
        when (splashViewEvent) {
            SplashViewEvent.NavigateToHome -> navigateToHome()
            SplashViewEvent.NavigateToLogin -> navigateToLogin()
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

    private fun showSplash() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                viewModel.redirect()
            }, 1500)
    }
}