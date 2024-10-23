package com.example.skillexchange.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R

class NewSkillsBottomSheetAdapter(private val items: List<ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Список для хранения выбранных позиций
    private val selectedPositions = mutableSetOf<Int>()

    inner class MainHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem.MainHeaderItem) {
            val textView = itemView.findViewById<TextView>(R.id.rv_main_header_new_skills)
            textView.text = item.text
        }
    }
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem.HeaderItem) {
            val textView = itemView.findViewById<TextView>(R.id.rv_header_new_skills)
            textView.text = item.text
        }
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListItem.TextItem) {
            val textView = itemView.findViewById<TextView>(R.id.item_tv_new_skills_bottomSheet_rv)
            textView.text = item.text
            // Установка фона в зависимости от того, выбран элемент или нет
            itemView.isSelected = selectedPositions.contains(adapterPosition)
            itemView.setBackgroundResource(if (itemView.isSelected) R.drawable.round_corners_blue else R.drawable.round_corners_light)
            textView.setTextColor(if (itemView.isSelected) Color.WHITE else Color.BLACK)

            // Обработка нажатия
            itemView.setOnClickListener {
                if (selectedPositions.contains(adapterPosition)) {
                    selectedPositions.remove(adapterPosition) // Убираем позицию, если она уже выбрана
                } else {
                    selectedPositions.add(adapterPosition) // Добавляем позицию, если она не выбрана
                }
                notifyItemChanged(adapterPosition) // Обновляем элемент, чтобы отобразить новое состояние
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.MainHeaderItem -> 0
            is ListItem.HeaderItem -> 1
            is ListItem.TextItem -> 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> MainHeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.bottom_new_skills_rv_main_header, parent, false)
            )
            1 -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.bottom_new_skills_rv_header, parent, false)
            )
            2 -> TextViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.bottom_new_skills_filter_rv_items, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.MainHeaderItem -> (holder as MainHeaderViewHolder).bind(item)
            is ListItem.HeaderItem -> (holder as HeaderViewHolder).bind(item)
            is ListItem.TextItem -> (holder as TextViewHolder).bind(item)
        }
    }

    // Метод для получения всех выбранных позиций
    fun getSelectedPositions(): List<Int> {
        return selectedPositions.toList()
    }

    // Храним выбранные позиции
//    private val selectedPositions = mutableSetOf<Int>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_new_skills_filter_rv_items, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return newSkillsList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = newSkillsList[position]
//        holder.item.text = item
//
//        // Устанавливаем цвет текста в зависимости от выбранного состояния
//        if (selectedPositions.contains(position)) {
//            holder.item.setBackgroundResource(R.drawable.round_corners_blue)
//            holder.item.setTextColor(Color.WHITE)
//        } else {
//            //holder.item.setTextColor(Color.BLACK)
//        }
//
//        holder.itemView.setOnClickListener {
//            // Переключаем состояние элемента
//            if (selectedPositions.contains(position)) {
//                selectedPositions.remove(position) // Убираем выбор, если нажат повторно
//            } else {
//                selectedPositions.add(position) // Добавляем элемент в выбранные
//            }
//            // Перемещаем выбранные элементы в начало списка
//            moveSelectedToFront()
//            notifyDataSetChanged() // Обновляем весь список
//        }
//    }
//
//    // Метод для перемещения выбранных элементов в начало списка
//    private fun moveSelectedToFront() {
//        // Создаем новый список, состоящий из выбранных элементов
//        val selectedItems = selectedPositions.map { newSkillsList[it] }.toMutableList()
//
//        // Убираем выбранные элементы из старого списка
//        newSkillsList = newSkillsList.filterIndexed { index, _ -> !selectedPositions.contains(index) }.toMutableList() as ArrayList<String>
//
//        // Добавляем выбранные элементы в начало списка
//        newSkillsList.addAll(0, selectedItems)
//        // Обнуляем выбранные позиции для корректного отображения
//        selectedPositions.clear()
//        // Устанавливаем новые позиции для выбранных элементов
//        selectedPositions.addAll(selectedItems.indices)
//    }
//
//    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val item: TextView = ItemView.findViewById(R.id.item_tv_new_skills_bottomSheet_rv)
//    }


}