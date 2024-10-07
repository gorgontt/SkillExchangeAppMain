package com.example.skillexchange


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.bodyapp.ui.dashboard.DashboardFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import javax.annotation.Nullable


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var loginBtn: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        auth = FirebaseAuth.getInstance()

        emailEt = view.findViewById(R.id.signin_login_edT)
        passwordEt = view.findViewById(R.id.signin_password_edT)

        loginBtn = view.findViewById(R.id.arrow_btn_signIn)

        loginBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val password: String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Successfully Logged In", Toast.LENGTH_LONG).show()

                        val intent = Intent(requireContext(), BottomNavActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()


                    } else {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }


        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val registerTV: View? = view?.findViewById(R.id.signin_register_btn_tv)


        registerTV?.setOnClickListener {

            val registerFragment = RegisterFragment()


            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_sign_in, registerFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }

}