package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.interfaces.UserRv

class SearchFragmentAdapter(private val userData: MutableList<UserRv>) : RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {

    //inner class SearchViewHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_userName_item_searchFrag)
        private val userAge: TextView = itemView.findViewById(R.id.tv_userAge_item_searchFrag)
        private val description: TextView = itemView.findViewById(R.id.description_list_item_searchFRag)
        //private val newSkillsRv: TextView = itemView.findViewById(R.id.new_skills_rv_listItemSerach)
        //private val mySkillsRv: TextView = itemView.findViewById(R.id.new_skills_rv_listItemSerach)

        fun bind(user: UserRv) {
            description.text = user.description
            userName.text = user.name
            userAge.text = user.age
            //newSkillsRv.text = user.newSkills
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_fragment, parent, false)
        return SearchViewHolder(view)

    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(userData[position])
        //val description: TextView = hol.findViewById(R.id.description_list_item_searchFRag)
        //description.text = UserRv.get(position).nameGroup

    }

    override fun getItemCount() = userData.size
}