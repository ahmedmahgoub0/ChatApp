package com.example.chatapp.ui.addRoom

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.ui.home.HomeActivity

class AddRoomActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAddRoomBinding
    private val viewModel: AddRoomViewModel by viewModels()
    private lateinit var spinnerAdapter: RoomCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this

        spinnerAdapter = RoomCategoryAdapter(viewModel.categoriesList)
        viewBinding.content.spinner.adapter = spinnerAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateToHome()
        return true
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRoom() {
        //
    }
}