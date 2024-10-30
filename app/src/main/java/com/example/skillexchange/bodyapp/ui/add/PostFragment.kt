package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostFragment : Fragment(), OnSkillsSelectedListener {

    private lateinit var _binding: FragmentPostBinding
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel
    private lateinit var skillsAdapter: SkillsAdapter
    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()
    private var db = Firebase.firestore // Инициализируем Firestore
    private var firebaseAuth = FirebaseAuth.getInstance()


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

        skillsAdapter = SkillsAdapter(mutableListOf())  // Измените на MutableList
//        binding.skillsRecyclerviewPostFragment.layoutManager = LinearLayoutManager(context)
        binding.skillsRecyclerviewPostFragment.adapter = skillsAdapter

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

        return view
    }

    override fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>) {
        // Получаем текущие навыки и добавляем новые
        val currentSkills = skillsAdapter.items.toMutableList()
        currentSkills.addAll(selectedSkills)
        skillsAdapter.updateSkills(currentSkills)  // Обновляем адаптер
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
                    val skills = document.get("skills") as? List<String> // Получите массив навыков
                    if (skills != null) {
                        mySkillsList.clear()
                        // преобразуйте строки в объекты Skill и добавьте их в mySkillsList
                        mySkillsList.addAll(skills.map { Skill(it) }) // Предположим, что у вас есть соответствующий конструктор
                        mySkillsAdapter.notifyDataSetChanged()
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки навыков", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
