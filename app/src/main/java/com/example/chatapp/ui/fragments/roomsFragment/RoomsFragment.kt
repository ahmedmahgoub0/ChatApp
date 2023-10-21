package com.example.chatapp.ui.fragments.roomsFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chatapp.databinding.FragmentRoomsBinding
import com.example.chatapp.model.Room
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.ui.common.showMessage

class RoomsFragment : Fragment() {

    private var viewBinding: FragmentRoomsBinding? = null
    private val viewModel: RoomsViewModel by viewModels()
    private var adapter = RoomsAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRoomsBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        subscribeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    private fun initViews() {
        viewBinding!!.vm = viewModel
        viewBinding!!.lifecycleOwner = viewLifecycleOwner
        viewBinding!!.roomsRv.adapter = adapter
        adapter.onItemClickListener =
            RoomsAdapter.OnItemClickListener { position, room ->
                val intent = Intent(requireActivity(), ChatActivity::class.java)
                intent.putExtra(Room.ROOM_KEY, room)
                startActivity(intent)
            }
    }

    private fun subscribeToLiveData() {
        viewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            showMessage(message)
        }
        viewModel.roomsLiveData.observe(viewLifecycleOwner) { rooms ->
            adapter.changeData(rooms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


}