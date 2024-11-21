package com.example.skillexchange.bodyapp.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.adapter.MyPostAdapter
import com.example.skillexchange.databinding.FragmentProfileBinding
import com.example.skillexchange.models.Skill
import com.example.skillexchange.models.UserRv
import com.example.skillexchange.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()

    private var postList = ArrayList<UserRv>()
    private lateinit var adapter: MyPostAdapter

    lateinit var viewModel: ChatAppViewModel
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = MyPostAdapter(requireContext(), postList)
        binding.profilePostsFragmentProfile.adapter = adapter

        //loadUserData()
        loadUserSkills()
        loadUserPosts()

        binding.settingsFab.setOnClickListener {
            val bottomSheet = SettingsFragment()
            bottomSheet.show(requireFragmentManager(), "BottomSheet")
            }

        mySkillsAdapter = MySkillsAdapter(mySkillsList)
        binding.mySkillsProfileFragment.adapter = mySkillsAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)

        viewModel.photoUrl.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext()).load(it).into(binding.profileImageView)
        })
        viewModel.name.observe(viewLifecycleOwner, Observer {userName->
            binding.collpasingToolbarProfile.title = userName
        })
    }

    private fun loadUserPosts() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("post").whereEqualTo("userId", userId).get()
                .addOnSuccessListener { documents ->
                    postList.clear()
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            val post = document.toObject(UserRv::class.java)
                            postList.add(post)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(activity, "Нет постов для отображения", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки постов", Toast.LENGTH_SHORT).show()
                }
        }
    }

//    private fun loadUserData() {
//        val currentUser = firebaseAuth.currentUser
//        if (currentUser != null) {
//            val userId = currentUser.uid
//            db.collection("users").document(userId).get()
//                .addOnSuccessListener { document ->
//                    if (document != null && document.exists()) {
//                        val userName = document.getString("name")
//                        val userPhotoUrl = document.getString("photoUrl")
//
//                        binding.collpasingToolbarProfile.title = userName
//                        Glide.with(this)
//                            .load(userPhotoUrl)
//                            .into(binding.profileImageView)
//                    } else {
//                        Toast.makeText(activity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .addOnFailureListener {
//                    Toast.makeText(activity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }

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

}

