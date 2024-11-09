package com.example.skillexchange.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.databinding.ListItemSearchFragmentBinding
import com.example.skillexchange.models.Skill
import com.example.skillexchange.models.UserRv

class MyPostAdapter (var context: Context, var postList: ArrayList<UserRv>): RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ListItemSearchFragmentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ListItemSearchFragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = postList[position]

        Glide.with(context).load(user.photoUrl).into(holder.binding.userImageListItemSearchFrag)
        holder.binding.tvUserNameItemSearchFrag.text = user.name
        holder.binding.tvUserAgeItemSearchFrag.text = user.age
        holder.binding.descriptionListItemSearchFRag.text = user.description
        holder.binding.mySkillsRvSearchFragment.adapter = MySkillsAdapter(user.mySkills.map { Skill(it) })
        holder.binding.newSkillsRvListItemSerach.adapter = SelectedSkillsAdapter(user.newSkills.map { ListItem.TextItem(it) }.toMutableList())


    }
}