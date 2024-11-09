package com.example.skillexchange.bodyapp.ui.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skillexchange.models.UserRv
import com.example.skillexchange.R
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.databinding.ActivityContentBinding
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

        val item = intent.getSerializableExtra("postItem") as UserRv
        binding.apply {
            //userIVCoontentActivity.setImageResource(item.photoUrl)
            nameContentActivity.text = item.name
            descriptionContentActivity.text = item.description
            rvMySkillsContentActivity.adapter = MySkillsAdapter(user.mySkills.map { Skill(it) })

        }
    }
}