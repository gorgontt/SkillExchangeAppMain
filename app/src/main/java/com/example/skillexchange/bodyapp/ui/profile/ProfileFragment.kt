package com.example.skillexchange.bodyapp.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.skillexchange.MainActivity
import com.example.skillexchange.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()
    private var db = Firebase.firestore // Инициализируем Firestore
    private var firebaseAuth = FirebaseAuth.getInstance() // Инициализируем FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.settingsFab.setOnClickListener {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                firebaseAuth.signOut()

                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        // Загрузка данных пользователя
        loadUserData()

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

                        // Обновление UI
                        binding.collpasingToolbarProfile.title = userName // Установите имя в CollapsingToolbar
                        Glide.with(this)
                            .load(userPhotoUrl) // Используйте библиотеку Glide для загрузки изображений
                            .into(binding.profileImageView) // Предполагается, что у вас есть ImageView с ID profileImageView
                    } else {
                        Toast.makeText(activity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
