package com.example.chatapp.ui.fragments.roomsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ItemRoomBinding
import com.example.chatapp.model.Category
import com.example.chatapp.model.Room

class RoomsAdapter(var roomsList: List<Room>?) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = roomsList?.get(position)
        holder.bind(room!!)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, room)
        }
    }

    override fun getItemCount(): Int = roomsList?.size ?: 0

    class ViewHolder(val itemBinding: ItemRoomBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        private val categories = Category.getCategoriesList()
        fun bind(room: Room) {
            itemBinding.roomImage.setImageResource(categories[room.categoryId!!.toInt()].imageId!!)
            itemBinding.roomName.text = room.roomName
        }

    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, room: Room)
    }

    fun changeData(rooms: List<Room>) {
        roomsList = rooms
        notifyDataSetChanged()
    }


}
