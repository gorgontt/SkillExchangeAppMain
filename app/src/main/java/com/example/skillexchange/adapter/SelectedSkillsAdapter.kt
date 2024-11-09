package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.R

class SelectedSkillsAdapter(val items: MutableList<ListItem.TextItem>) : RecyclerView.Adapter<SelectedSkillsAdapter.SkillsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return SkillsViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateSkills(newSkills: List<ListItem.TextItem>) {
        items.clear()
        items.addAll(newSkills)
        notifyDataSetChanged()
    }

    class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text_item_skill)

        fun bind(item: ListItem.TextItem) {
            textView.text = item.text
        }
    }
}



















