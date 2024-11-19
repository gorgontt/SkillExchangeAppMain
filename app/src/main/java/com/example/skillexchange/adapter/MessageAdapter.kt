package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.databinding.MessagesListItemBinding
import com.example.skillexchange.models.Message

class MessageAdapter : ListAdapter<Message, MessageAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(var binding: MessagesListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: Message) = with(binding){
            message.text = user.message
            username.text = user.nameUser
        }

        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(MessagesListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}