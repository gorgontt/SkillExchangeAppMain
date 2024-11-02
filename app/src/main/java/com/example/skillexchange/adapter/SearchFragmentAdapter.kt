package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillexchange.Models.User
import com.example.skillexchange.R
import com.example.skillexchange.databinding.ListItemSearchFragmentBinding
import com.example.skillexchange.interfaces.Skill
import com.example.skillexchange.interfaces.UserRv
import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SearchFragmentAdapter(private val context: Context, var userData: ArrayList<UserRv>) : RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(var binding: ListItemSearchFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val userName: TextView = itemView.findViewById(R.id.tv_userName_item_searchFrag)
//        private val userAge: TextView = itemView.findViewById(R.id.tv_userAge_item_searchFrag)
//        private val description: TextView = itemView.findViewById(R.id.description_list_item_searchFRag)
//        private val userProfileImage: ImageView = itemView.findViewById(R.id.userImage_listItemSearchFrag)
//        private val mySkillsRv: RecyclerView = itemView.findViewById(R.id.my_skills_rv_searchFragment)
//        private val newSkillsRv: RecyclerView = itemView.findViewById(R.id.new_skills_rv_listItemSerach)
//
//        fun bind(user: UserRv) {
//            description.text = user.description
//            userName.text = user.name
//            userAge.text = user.age
//            Glide.with(itemView.context)
//                .load(user.profileImageUrl)
//                .into(userProfileImage)
//
//            val mySkillsAdapter = MySkillsAdapter(user.mySkills.map { Skill(it) })
//            mySkillsRv.adapter = mySkillsAdapter
//
//            val newSkillsAdapter = SkillsAdapter(user.newSkills.map { ListItem.TextItem(it) }.toMutableList())
//            newSkillsRv.adapter = newSkillsAdapter
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        var binding = ListItemSearchFragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val user = userData[position]

        Glide.with(context).load(user.photoUrl).into(holder.binding.userImageListItemSearchFrag)
        holder.binding.tvUserNameItemSearchFrag.text = user.name
        holder.binding.tvUserAgeItemSearchFrag.text = user.age
        holder.binding.descriptionListItemSearchFRag.text = user.description
        holder.binding.mySkillsRvSearchFragment.adapter = MySkillsAdapter(user.mySkills.map { Skill(it) })
        holder.binding.newSkillsRvListItemSerach.adapter = SkillsAdapter(user.newSkills.map { ListItem.TextItem(it) }.toMutableList())
    }

    override fun getItemCount() = userData.size
}