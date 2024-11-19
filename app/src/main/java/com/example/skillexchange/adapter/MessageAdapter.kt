package com.example.skillexchange.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.databinding.ListItemSearchFragmentBinding

class MessageAdapter(var messageList: MutableList<android.os.Message>): RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ListItemSearchFragmentBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyPostAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}