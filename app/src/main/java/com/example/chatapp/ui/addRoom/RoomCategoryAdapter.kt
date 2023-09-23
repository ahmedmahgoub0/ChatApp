package com.example.chatapp.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatapp.databinding.ItemRoomCateogryBinding
import com.example.chatapp.model.Category

class RoomCategoryAdapter(val items: List<Category>) : BaseAdapter() {
    override fun getCount(): Int = items.size ?: 0

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = items[position].id!!.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        if (view == null) {
            // create view holder
            val itemBinding = ItemRoomCateogryBinding.inflate(
                LayoutInflater.from(parent?.context), parent, false
            )
            viewHolder = ViewHolder(itemBinding)
            itemBinding.root.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        // bind view holder
        viewHolder.bind(items[position])
        return viewHolder.itemBinding.root
    }

    class ViewHolder(val itemBinding: ItemRoomCateogryBinding) {
        fun bind(item: Category) {
            itemBinding.categoryImage
                .setImageResource(item.imageId!!)
            itemBinding.categoryTitle.text = item.name!!
        }
    }

}