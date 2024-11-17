package com.example.skillexchange.bodyapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.skillexchange.MainActivity
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.bodyapp.ui.search.SearchViewModel
import com.example.skillexchange.databinding.FragmentSettingsBinding
import com.example.skillexchange.models.Skill
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SettingsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageUri: Uri

    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()



    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = view.parent as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadUserData()
        loadUserSkills()

        binding.editFab.setOnClickListener {
            openGallery()
        }

        binding.exitBtn.setOnClickListener {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firebaseAuth.signOut()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        mySkillsAdapter = MySkillsAdapter(mySkillsList)
        binding.mySkillsSettingsFragment.adapter = mySkillsAdapter

        return root
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
                        val userAge = document.getString("age")

                        binding.nameSettings.text = userName
                        binding.ageSettingFragment.text = userAge
                        Glide.with(this)
                            .load(userPhotoUrl)
                            .into(binding.settingsImageView)
                    } else {
                        Toast.makeText(activity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            uploadImageToFirestore()
        }
    }

    private fun uploadImageToFirestore() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val storageRef = Firebase.storage.reference.child("images/${userId}.jpg")

            storageRef.putFile(imageUri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        updateUserPhotoUrl(uri.toString(), userId)

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUserPhotoUrl(photoUrl: String, userId: String) {
        db.collection("users").document(userId)
            .update("photoUrl", photoUrl)
            .addOnSuccessListener {
                Toast.makeText(activity, "Фото обновлено", Toast.LENGTH_SHORT).show()
                loadUserData() // Чтобы обновить отображаемое фото
                // Обновление в SearchViewModel
                val searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
                searchViewModel.updateUserList(db.collection("post"))
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Ошибка обновления URL фотографии", Toast.LENGTH_SHORT).show()
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