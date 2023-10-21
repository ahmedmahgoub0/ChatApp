package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.common.showMessage
import com.example.chatapp.ui.fragments.roomsFragment.RoomsFragment
import com.example.chatapp.ui.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private var viewBinding: ActivityHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        viewBinding!!.vm = viewModel
        viewBinding!!.lifecycleOwner = this

        showFragment(RoomsFragment())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun subscribeToLiveData() {
        viewModel.eventLiveData.observe(this, ::handleEvent)
        viewModel.messageLiveData.observe(this) { message ->
            showMessage(message)
        }
    }

    private fun handleEvent(viewEvent: HomeViewEvent?) {
        when (viewEvent) {
            HomeViewEvent.NavigateToAddRoom -> {
                navigateToAddRoom()
            }

            HomeViewEvent.NavigateToLogin -> {
                navigateToLogin()
            }

            else -> {}
        }
    }


    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}