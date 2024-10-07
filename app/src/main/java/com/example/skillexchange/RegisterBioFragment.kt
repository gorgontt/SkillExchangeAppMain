package com.example.skillexchange

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterBioFragment : Fragment() {

    private var db = Firebase.firestore

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var ivPhoto: ImageView
    private lateinit var btnFemale: TextView
    private lateinit var btnMale: TextView

    private lateinit var btnGo: ImageView

    private var selectedGender: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_bio, container, false)

        etName = view.findViewById(R.id.username_edT_regbio)
        etAge = view.findViewById(R.id.userage_edT_regbio)
        ivPhoto = view.findViewById(R.id.userphoto_iv_regbio)
        btnFemale = view.findViewById(R.id.female_btn_regbio)
        btnMale = view.findViewById(R.id.male_btn_regbio)
        btnGo = view.findViewById(R.id.arrow_btn_go_regbio)

        btnFemale.setOnClickListener {
            selectedGender = "female"
            btnFemale.setBackgroundResource(R.drawable.button_black_corners) // Измените фон на выбранный, если нужно
            btnFemale.setTextColor(Color.WHITE)
            btnMale.setBackgroundResource(R.drawable.button_stroke_radius) // Сброс фона для другого варианта
        }

        btnMale.setOnClickListener {
            selectedGender = "male"
            btnMale.setBackgroundResource(R.drawable.button_black_corners) // Измените фон на выбранный, если нужно
            btnMale.setTextColor(Color.WHITE)
            btnFemale.setBackgroundResource(R.drawable.button_stroke_radius) // Сброс фона для другого варианта
        }

        btnGo.setOnClickListener {

            val sEtName = etName.text.toString().trim()
            val sEtAge = etAge.text.toString().trim()

            if (selectedGender == null) {
                Toast.makeText(activity, "Выберите пол", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userMap = hashMapOf(
                "name" to sEtName,
                "age" to sEtAge,
                "gender" to selectedGender
            )

            db.collection("users").add(userMap)
                .addOnSuccessListener {
                    Toast.makeText(activity, "Good", Toast.LENGTH_LONG).show()
                    etName.text.clear()
                    etAge.text.clear()
                    selectedGender = null
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Bad", Toast.LENGTH_LONG).show()
                }
        }

        return view
    }


}