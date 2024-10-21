package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R

class NewSkillsBottomSheetAdapter(private var newSkillsList: ArrayList<String>): RecyclerView.Adapter<NewSkillsBottomSheetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_new_skills_filter_rv_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newSkillsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = newSkillsList[position]
        holder.item.text = item
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val item: TextView = ItemView.findViewById(R.id.item_tv_new_skills_bottomSheet_rv)
    }
}