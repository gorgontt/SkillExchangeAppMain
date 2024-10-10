package com.example.skillexchange

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class RegisterBioFragment : Fragment() {

    private var db = Firebase.firestore
    private lateinit var storageRef: StorageReference

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var ivPhoto: ImageView
    private lateinit var btnFemale: TextView
    private lateinit var btnMale: TextView

    private lateinit var btnGo: ImageView
    private lateinit var btnPickPhoto: ImageView

    private var selectedGender: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_bio, container, false)

        storageRef = FirebaseStorage.getInstance().getReference("UsersPhotos")

        etName = view.findViewById(R.id.username_edT_regbio)
        etAge = view.findViewById(R.id.userage_edT_regbio)
        ivPhoto = view.findViewById(R.id.userphoto_iv_regbio)
        btnFemale = view.findViewById(R.id.female_btn_regbio)
        btnMale = view.findViewById(R.id.male_btn_regbio)
        btnGo = view.findViewById(R.id.arrow_btn_go_regbio)
        btnPickPhoto = view.findViewById(R.id.pick_photo_regbio)

        btnPickPhoto.setOnClickListener {
            openGallery()
        }

        btnFemale.setOnClickListener {
            selectedGender = "female"
            btnFemale.setBackgroundResource(R.drawable.button_black_corners)
            btnFemale.setTextColor(Color.WHITE)
            btnMale.setBackgroundResource(R.drawable.button_stroke_radius)
        }

        btnMale.setOnClickListener {
            selectedGender = "male"
            btnMale.setBackgroundResource(R.drawable.button_black_corners)
            btnMale.setTextColor(Color.WHITE)
            btnFemale.setBackgroundResource(R.drawable.button_stroke_radius)
        }

        btnGo.setOnClickListener {
            uploadImageToFirebase()
        }

        return view
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
            ivPhoto.setImageURI(imageUri) // Отображение выбранного изображения в ImageView
        }
    }

    private fun uploadImageToFirebase() {
        val sEtName = etName.text.toString().trim()
        val sEtAge = etAge.text.toString().trim()

        if (imageUri == null) {
            Toast.makeText(activity, "Пожалуйста, выберите фото", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedGender == null) {
            Toast.makeText(activity, "Выберите пол", Toast.LENGTH_SHORT).show()
            return
        }

        val userMap = hashMapOf(
            "name" to sEtName,
            "age" to sEtAge,
            "gender" to selectedGender,
            "photoUrl" to "" // Временно пустая строка для URL фото
        )

        // Загружаем изображение в Firebase Storage
        val fileRef = storageRef.child("$sEtName.jpg")
        fileRef.putFile(imageUri!!)
            .addOnSuccessListener {
                // Получаем URL загруженного изображения
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    userMap["photoUrl"] = uri.toString() // Устанавливаем URL в userMap
                    saveUserDataToFirestore(userMap)
                }
                    .addOnFailureListener {
                        Toast.makeText(activity, "Ошибка получения URL изображения", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserDataToFirestore(userMap: HashMap<String, String?>) {
        db.collection("users").add(userMap)
            .addOnSuccessListener {
                Toast.makeText(activity, "Данные успешно сохранены", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etAge.text.clear()
                ivPhoto.setImageURI(null) // Сброс изображения
                selectedGender = null
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Ошибка сохранения данных", Toast.LENGTH_LONG).show()
            }
    }
}
