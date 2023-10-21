package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.databinding.ItemMessageReceivedBinding
import com.example.chatapp.databinding.ItemMessageSentBinding
import com.example.chatapp.model.ChatMessage
import com.example.chatapp.ui.SessionProvider

class MessagesAdapter(var messagesList: MutableList<ChatMessage>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val message = messagesList[position]
        return if (message.senderId == SessionProvider.user?.id) {
            MessageType.SentMessage.value
        } else {
            MessageType.ReceivedMessage.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MessageType.SentMessage.value) {
            val itemBinding = ItemMessageSentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            SentMessageViewHolder(itemBinding)
        } else {
            val itemBinding = ItemMessageReceivedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ReceivedMessageViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SentMessageViewHolder -> {
                holder.bind(messagesList[position])
            }

            is ReceivedMessageViewHolder -> {
                holder.bind(messagesList[position])
            }
        }
    }

    override fun getItemCount(): Int = messagesList.size
    fun addNewMessages(newMessages: List<ChatMessage>?) {
        if (newMessages == null) return
        val oldSize = messagesList.size
        messagesList.addAll(newMessages)
        notifyItemRangeInserted(oldSize, newMessages.size)
    }

    class SentMessageViewHolder(val itemBinding: ItemMessageSentBinding) :
        ViewHolder(itemBinding.root) {
        fun bind(message: ChatMessage) {
            itemBinding.message = message
            itemBinding.executePendingBindings()
        }
    }

    class ReceivedMessageViewHolder(val itemBinding: ItemMessageReceivedBinding) :
        ViewHolder(itemBinding.root) {
        fun bind(message: ChatMessage) {
            itemBinding.message = message
            itemBinding.executePendingBindings()
        }
    }

    enum class MessageType(val value: Int) {
        SentMessage(100),
        ReceivedMessage(200)
    }
}