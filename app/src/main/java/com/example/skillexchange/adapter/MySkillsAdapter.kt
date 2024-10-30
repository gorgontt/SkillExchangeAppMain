package com.example.skillexchange.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.interfaces.Skill

class MySkillsAdapter(private val skills: List<Skill>) : RecyclerView.Adapter<MySkillsAdapter.SkillViewHolder>() {

    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val skillName: TextView = itemView.findViewById(R.id.text_item_skill)

        fun bind(skill: Skill) {
            skillName.text = skill.name
            //skillName.setTextColor(Color.WHITE)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.bind(skills[position])
    }

    override fun getItemCount() = skills.size
}
