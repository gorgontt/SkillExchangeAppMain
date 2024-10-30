package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.bodyapp.ui.search.SearchFragment
import com.example.skillexchange.interfaces.UserRv

class SearchFragmentAdapter(private val userData: MutableList<UserRv>) : RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_userName_item_searchFrag)
        private val userAge: TextView = itemView.findViewById(R.id.tv_userAge_item_searchFrag)

        fun bind(user: UserRv) {
            userName.text = user.name
            userAge.text = user.age
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_fragment, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(userData[position])
    }

    override fun getItemCount() = userData.size
}