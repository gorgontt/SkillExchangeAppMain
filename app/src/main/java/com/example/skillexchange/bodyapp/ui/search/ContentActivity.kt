package com.example.skillexchange.bodyapp.ui.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.skillexchange.models.UserRv
import com.example.skillexchange.R
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.adapter.SelectedSkillsAdapter
import com.example.skillexchange.databinding.ActivityContentBinding
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.models.Skill

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        // Получаем переданные данные
        val item = intent.getSerializableExtra("postItem") as UserRv

        // Если передали скиллы, получим их откуда надо
        val skills = intent.getStringArrayListExtra("skills")
        val newSkills = intent.getStringArrayListExtra("newSkills")


        binding.apply {
            Glide.with(this@ContentActivity).load(item.photoUrl).into(userIVCoontentActivity)
            nameContentActivity.text = item.name
            descriptionContentActivity.text = item.description
            userAgeContentActivity.text = item.age

            // Устанавливаем адаптер скиллов
            skills?.let {
                rvMySkillsContentActivity.adapter = MySkillsAdapter(it.map { skill -> Skill(skill) })
            }
            newSkills?.let {
                rvNewSkillsContentActivity.adapter = SelectedSkillsAdapter(it.map { newSkill -> ListItem.TextItem(newSkill) }.toMutableList())
            }
        }

        binding.messageBtn.setOnClickListener {
//            val dialogIntent = Intent(this, DialogActivity::class.java).apply {
//                putExtra("username", item.name)
//                putExtra("photoUrl", item.photoUrl)
//            }
//            startActivity(dialogIntent)
        }
    }
}