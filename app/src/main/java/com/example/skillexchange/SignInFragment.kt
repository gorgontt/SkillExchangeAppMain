package com.example.skillexchange


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import javax.annotation.Nullable


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var registerBtn: TextView
    private lateinit var loginBtn: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        auth = FirebaseAuth.getInstance()

        emailEt = view.findViewById(R.id.signin_login_edT)
        passwordEt = view.findViewById(R.id.signin_password_edT)

        loginBtn = view.findViewById(R.id.arrow_btn_signIn)
        registerBtn = view.findViewById(R.id.signin_register_btn_tv)

        loginBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val password: String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        registerBtn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //    val arrowButton: View? = view?.findViewById(R.id.arrow_btn_image)
        //    val registerTV: View? = view?.findViewById(R.id.register_tv_from_signIn)

//    //    arrowButton?.setOnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }

//        registerTV?.setOnClickListener {
//
//            val registerFragment = RegisterFragment()
//
//
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.frame_sign_in, registerFragment)
////            transaction?.addToBackStack(null)
//            transaction?.commit()
//        }
//    }
    }

}