package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.interfaces.Skill
import com.example.skillexchange.interfaces.UserRv

class SearchFragmentAdapter(private val userData: MutableList<UserRv>) : RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {

    //inner class SearchViewHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_userName_item_searchFrag)
        private val userAge: TextView = itemView.findViewById(R.id.tv_userAge_item_searchFrag)
        private val description: TextView = itemView.findViewById(R.id.description_list_item_searchFRag)
        private val mySkillsRv: RecyclerView = itemView.findViewById(R.id.my_skills_rv_searchFragment)
        private val newSkillsRv: RecyclerView = itemView.findViewById(R.id.new_skills_rv_listItemSerach)

        fun bind(user: UserRv) {
            description.text = user.description
            userName.text = user.name
            userAge.text = user.age

            // Создаем адаптер для mySkills
            val mySkillsAdapter = MySkillsAdapter(user.mySkills.map { Skill(it) }) // Преобразуем mySkills в ваш объект Skill
            mySkillsRv.adapter = mySkillsAdapter

            // Создаем адаптер для newSkills
            val newSkillsAdapter = SkillsAdapter(user.newSkills.map { ListItem.TextItem(it) }.toMutableList()) // Преобразуем newSkills в ваши объекты ListItem.TextItem
            newSkillsRv.adapter = newSkillsAdapter
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