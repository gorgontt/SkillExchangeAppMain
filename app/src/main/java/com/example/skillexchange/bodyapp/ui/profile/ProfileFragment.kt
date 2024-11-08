package com.example.skillexchange.bodyapp.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.skillexchange.MainActivity
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.adapter.ProfilePostAdapter
import com.example.skillexchange.adapter.SearchFragmentAdapter
import com.example.skillexchange.databinding.FragmentProfileBinding
import com.example.skillexchange.interfaces.Skill
import com.example.skillexchange.interfaces.UserRv
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()

    private var postList = ArrayList<UserRv>()
    private lateinit var adapter: ProfilePostAdapter

    private val viewModel: ProfileViewModel by viewModels()
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = ProfilePostAdapter(requireContext(), postList)
        binding.profilePostsFragmentProfile.adapter = adapter

        loadUserData()
        loadUserSkills()
        loadUserPosts()

        binding.settingsFab.setOnClickListener {
            val bottomSheet = SettingsFragment()
            bottomSheet.show(requireFragmentManager(), "BottomSheet")
            }

//            val currentUser = firebaseAuth.currentUser
//            if (currentUser != null) {
//                firebaseAuth.signOut()
//                val intent = Intent(activity, MainActivity::class.java)
//                startActivity(intent)
//                activity?.finish()
//            }

        mySkillsAdapter = MySkillsAdapter(mySkillsList)
        binding.mySkillsProfileFragment.adapter = mySkillsAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomSheet = ProfileFragment()
//        dialog.setOnShowListener { dialog ->
//            val d = dialog as BottomSheetDialog
//            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
//            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//
//            bottomSheetBehavior.peekHeight = bottomSheet.height
//        }
    }

    private fun loadUserPosts() {

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("post").document(userId).get()
                .addOnSuccessListener { documents ->
                    postList.clear()
                        val post = documents.toObject(UserRv::class.java)
                    if (post != null) {
                        postList.add(post)
                    }else{
                        Toast.makeText(activity, "Ошибка загрузки поста", Toast.LENGTH_SHORT).show()
                    }
                        adapter.notifyDataSetChanged()

                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки навыков", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun loadUserData() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val userName = document.getString("name")
                        val userPhotoUrl = document.getString("photoUrl")

                        binding.collpasingToolbarProfile.title = userName
                        Glide.with(this)
                            .load(userPhotoUrl)
                            .into(binding.profileImageView)
                    } else {
                        Toast.makeText(activity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
                }
        }
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

}

