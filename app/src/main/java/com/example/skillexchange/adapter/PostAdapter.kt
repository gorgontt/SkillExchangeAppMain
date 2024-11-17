package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillexchange.databinding.ListItemSearchFragmentBinding
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.skillexchange.R
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.bodyapp.ui.messages.DialogActivity
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.models.Skill
import com.example.skillexchange.models.UserRv

class PostAdapter(val itemListener: PostItemListener, private val context: Context, var userData: ArrayList<UserRv>) : RecyclerView.Adapter<PostAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ListItemSearchFragmentBinding.bind(item)

        fun bind(user: UserRv, itemListener: PostItemListener) = with(binding){
            Glide.with(context).load(user.photoUrl).into(userImageListItemSearchFrag)
            tvUserNameItemSearchFrag.text = user.name
            tvUserAgeItemSearchFrag.text = user.age
            descriptionListItemSearchFRag.text = user.description
            mySkillsRvSearchFragment.adapter = MySkillsAdapter(user.mySkills.map { Skill(it) })
            newSkillsRvListItemSerach.adapter = SelectedSkillsAdapter(user.newSkills.map { ListItem.TextItem(it) }.toMutableList())

            itemView.setOnClickListener {
                itemListener.onItemClick(user, user.mySkills, user.newSkills)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_fragment, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(userData[position], itemListener)

    }

    override fun getItemCount() = userData.size

    interface PostItemListener {
        fun onItemClick(user: UserRv, skills: List<String>, newSkills: List<String>)
        //fun onItemClick(user: UserRv)
    }
}