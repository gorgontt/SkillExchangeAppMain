package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.adapter.ListItem
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.adapter.SkillsAdapter
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentPostBinding
import com.example.skillexchange.interfaces.OnSkillsSelectedListener
import com.example.skillexchange.interfaces.Skill
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostFragment : Fragment(), OnSkillsSelectedListener {

    private lateinit var _binding: FragmentPostBinding
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel
    private lateinit var skillsAdapter: SkillsAdapter
    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var etDescription: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val view = binding.root

        skillsAdapter = SkillsAdapter(mutableListOf())
        binding.skillsRecyclerviewPostFragment.adapter = skillsAdapter
        etDescription = binding.descriptionPostFragment

        binding.addNewSkillsPostFragment.setOnClickListener {
            if (isAdded && !isDetached) {
                val skillsBottomSheet = SkillsBottomSheetDialog()
                skillsBottomSheet.setTargetFragment(this, 1)
                skillsBottomSheet.show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
            }
        }

        mySkillsAdapter = MySkillsAdapter(mySkillsList)
        binding.mySkillsRecyclerviewPostFragment.adapter = mySkillsAdapter
        loadUserSkills()

        binding.publishButton.setOnClickListener {
            loadPost()
        }

        return view
    }

    override fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>) {
        val currentSkills = skillsAdapter.items.toMutableList()
        currentSkills.addAll(selectedSkills)
        skillsAdapter.updateSkills(currentSkills)
    }

    companion object {
        fun newInstance() = PostFragment()
    }

    private fun loadUserSkills(){
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val skills = document.get("skills") as? List<String>
                    if (skills != null) {
                        mySkillsList.clear()
                        mySkillsList.addAll(skills.map { Skill(it) })
                        mySkillsAdapter.notifyDataSetChanged()
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки навыков", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loadPost(){
        val description = etDescription.text.toString().trim()
        //val selectedSkills = skillsAdapter.items.map { it.t } // предполагаем, что у вас есть свойство name у Skill
        val selectedSkills = skillsAdapter.items.filterIsInstance<ListItem.TextItem>().map { it.text }

        if (description.isEmpty() || selectedSkills.isEmpty()) {
            Toast.makeText(activity, "Пожалуйста, заполните все поля.", Toast.LENGTH_SHORT).show()
            return
        }

        // Создаем объект для добавления в коллекцию "post"
        val postMap = hashMapOf(
            "description" to description,
            "skills" to selectedSkills,
            "userId" to firebaseAuth.currentUser?.uid,
            "timestamp" to FieldValue.serverTimestamp() // добавляем временную метку
        )

        // Добавляем пост в коллекцию "post"
        db.collection("post")
            .add(postMap)
            .addOnSuccessListener {
                Toast.makeText(activity, "Пост успешно добавлен!", Toast.LENGTH_SHORT).show()
                etDescription.text.clear() // очищаем поле после добавления
                //skillsAdapter.clearSkills() // метод для очистки выбранных навыков, если такой существует
            }
            .addOnFailureListener { e ->
                Toast.makeText(activity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

