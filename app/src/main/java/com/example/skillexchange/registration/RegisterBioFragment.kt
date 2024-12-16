package com.example.skillexchange.registration

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.R
import com.example.skillexchange.adapter.SelectedSkillsAdapter
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentRegisterBioBinding
import com.example.skillexchange.interfaces.OnSkillsSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterBioFragment : Fragment(), OnSkillsSelectedListener {
    private lateinit var _binding: FragmentRegisterBioBinding
    private val binding get() = _binding!!
    private var db = Firebase.firestore
    private var firebaseAuth = Firebase.auth
    private lateinit var auth : FirebaseAuth
    private lateinit var storageRef: StorageReference
    private var progressBar: ProgressBar? = null
    private var selectedGender: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    private lateinit var skillsAdapter: SelectedSkillsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBioBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar?.visibility = View.INVISIBLE


        storageRef = FirebaseStorage.getInstance().getReference("UsersPhotos")
        progressBar = view.findViewById<ProgressBar>(R.id.progress_Bar_regBio)

        skillsAdapter = SelectedSkillsAdapter(mutableListOf())
        binding.recyclerviewRegBioFragment.adapter = skillsAdapter

        binding.addMySkillsRegBioFragment.setOnClickListener {
            if (isAdded && !isDetached) {
                val skillsBottomSheet = SkillsBottomSheetDialog()
                skillsBottomSheet.setTargetFragment(this, 1)
                skillsBottomSheet.show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
            }
        }
        Buttons()
        return view
    }

    private fun Buttons() = with(binding){
        pickPhotoRegbio.setOnClickListener {
            openGallery()
        }

        femaleBtnRegbio.setOnClickListener {
            selectedGender = "female"
            femaleBtnRegbio.setBackgroundResource(R.drawable.button_black_corners)
            femaleBtnRegbio.setTextColor(Color.WHITE)
            maleBtnRegbio.setBackgroundResource(R.drawable.button_stroke_radius)
        }

        maleBtnRegbio.setOnClickListener {
            selectedGender = "male"
            maleBtnRegbio.setBackgroundResource(R.drawable.button_black_corners)
            maleBtnRegbio.setTextColor(Color.WHITE)
            femaleBtnRegbio.setBackgroundResource(R.drawable.button_stroke_radius)
        }

        arrowBtnGoRegbio.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            uploadImageToFirebase()
        }
    }

    override fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>) {
        val currentSkills = skillsAdapter.items.toMutableList()
        currentSkills.addAll(selectedSkills)
        skillsAdapter.updateSkills(currentSkills)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.arrowBtnBackRegbio?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.frame_regbio,
                    RegisterFragment()
                )
                .commit()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Выберите фото"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.userphotoIvRegbio.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebase() {
        val sEtName = binding.usernameEdTRegbio.text.toString().trim()
        val sEtAge = binding.userageEdTRegbio.text.toString().trim()

        if (imageUri == null) {
            Toast.makeText(activity, "Пожалуйста, выберите фото", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedGender == null) {
            Toast.makeText(activity, "Выберите пол", Toast.LENGTH_SHORT).show()
            return
        }

        val skills = skillsAdapter.items.filterIsInstance<ListItem.TextItem>().map { it.text }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val userMap = hashMapOf(
            "name" to sEtName,
            "age" to sEtAge,
            "gender" to selectedGender,
            "photoUrl" to "",
            "skills" to skills,
            "userId" to user!!.uid!!,
            "status" to "default"
        )

        // Загружаем изображение в Firebase Storage
        val fileRef = storageRef.child("$sEtName.jpg")
        fileRef.putFile(imageUri!!)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    userMap["photoUrl"] = uri.toString() // Устанавливаем URL в userMap
                    saveUserDataToFirestore(userMap)
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка получения URL изображения", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(activity, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserDataToFirestore(userMap: HashMap<String, Any?>) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Регистрация прошла успешно", Toast.LENGTH_LONG).show()
                    progressBar?.visibility = View.INVISIBLE
                    val intent = Intent(requireContext(), BottomNavActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка сохранения данных", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(activity, "Ошибка: пользователь не авторизован", Toast.LENGTH_SHORT).show()
        }
    }


}
