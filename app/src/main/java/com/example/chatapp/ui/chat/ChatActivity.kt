package com.example.chatapp.ui.chat

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.Room
import com.google.firebase.firestore.ListenerRegistration

class ChatActivity : AppCompatActivity() {

    private var viewBinding: ActivityChatBinding? = null
    private val viewModel: ChatViewModel by viewModels()
    private var messagesAdapter = MessagesAdapter(mutableListOf())
    private var room: Room? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initParams()
        initViews()
        subscribeToLiveData()
    }

    private fun initParams() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            room = intent.getParcelableExtra(Room.ROOM_KEY, Room::class.java)
        } else {
            @Suppress("DEPRECATION")
            room = intent.getParcelableExtra(Room.ROOM_KEY) as Room?
        }
    }

    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewBinding!!.vm = viewModel
        viewBinding!!.lifecycleOwner = this
        (viewBinding!!.content.messagesRv.layoutManager as LinearLayoutManager)
            .stackFromEnd = true
        viewBinding!!.content.messagesRv.adapter = messagesAdapter

        viewBinding!!.roomName.text = room?.roomName

        setSupportActionBar(viewBinding!!.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    private fun subscribeToLiveData() {
        viewModel.newMessagesLiveData.observe(this) { newMessages ->
            messagesAdapter.addNewMessages(newMessages)
            viewBinding!!.content.messagesRv.smoothScrollToPosition(messagesAdapter.itemCount)
        }
        viewModel.toastLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    var listener: ListenerRegistration? = null
    override fun onStart() {
        super.onStart()
        if (listener == null)
            viewModel.changeRoom(room)
    }

    override fun onStop() {
        super.onStop()
        listener?.remove()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        finish()
        return true
    }

}
