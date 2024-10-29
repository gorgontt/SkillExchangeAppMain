package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R

class SkillsAdapter(val items: MutableList<ListItem>) : RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return SkillsViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        // Здесь связываем holder с данными из items
        val item = items[position]
        holder.bind(item)
    }

    // Добавленный метод для обновления навыков
    fun updateSkills(newSkills: List<ListItem>) {
        items.clear()  // Очистите старые навыки
        items.addAll(newSkills)  // Добавьте новые навыки
        notifyDataSetChanged()  // Уведомите о изменениях
    }

    // Объявляем внутренний класс ViewHolder
    class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text_item_skill)

        fun bind(item: ListItem) {
            // Убедитесь, что ListItem.TextItem содержит свойство text
            if (item is ListItem.TextItem) {
                textView.text = item.text
            }
        }
    }
}



















