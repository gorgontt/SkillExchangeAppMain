package com.example.skillexchange

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import javax.annotation.Nullable


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var signUpBtn: ImageView
    private lateinit var loginBtn: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()

        emailEt = view.findViewById(R.id.register_login)
        passwordEt = view.findViewById(R.id.register_password)

        loginBtn = view.findViewById(R.id.register_login_btn)
        signUpBtn = view.findViewById(R.id.register_arrow_btn)

        signUpBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val password: String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Successfully Registered",
                                Toast.LENGTH_LONG
                            ).show()
                            // Переход к MainActivity или другому фрагменту
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame_register,
                                    RegisterBioFragment()
                                ) // Предполагаем, что вы используете контейнер для смены фрагментов
                                .addToBackStack(null)
                                .commit()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Registration Failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }

        loginBtn.setOnClickListener {
            // Переход к LoginActivity или другому фрагменту
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_register, SignInFragment()) // Предполагаем, что вы используете контейнер для смены фрагментов
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


       // val arrowButton: View? = view?.findViewById(R.id.arrow_btn_image_registerFrag)
        //val signInBtn: View? = view?.findViewById(R.id.sign_in_tv_from_registerFrag)
//
//        arrowButton?.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }

//        signInBtn?.setOnClickListener {
//            val signInrFragment = SignInFragment()
//
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.frame_register, signInrFragment)
//            transaction?.commit()
//        }
    }

}