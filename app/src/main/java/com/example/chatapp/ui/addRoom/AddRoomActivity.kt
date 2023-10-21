package com.example.chatapp.ui.addRoom

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.model.Message
import com.example.chatapp.ui.common.showLoadingProgressDialog
import com.example.chatapp.ui.common.showMessage

class AddRoomActivity : AppCompatActivity() {

    private var viewBinding: ActivityAddRoomBinding? = null
    private val viewModel: AddRoomViewModel by viewModels()
    private lateinit var spinnerAdapter: RoomCategoryAdapter
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        setSupportActionBar(viewBinding!!.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        viewBinding!!.vm = viewModel
        viewBinding!!.lifecycleOwner = this

        spinnerAdapter = RoomCategoryAdapter(viewModel.categoriesList)
        viewBinding!!.content.spinner.adapter = spinnerAdapter
        viewBinding!!.content.spinner.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onItemSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(this) { message ->
            showMessage(message)
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

    private fun handleEvents(addRoomEvent: AddRoomEvent?) {
        when (addRoomEvent) {
            AddRoomEvent.NavigateToHome -> {
                navigateToHome()
            }

            else -> {}
        }
    }


    private fun navigateToHome() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateToHome()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}