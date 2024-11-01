package com.example.skillexchange


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.databinding.FragmentSignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import javax.annotation.Nullable


class SignInFragment : Fragment() {

    private lateinit var _binding: FragmentSignInBinding
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar = view.findViewById<ProgressBar>(R.id.progress_Bar_signIn)
        progressBar?.visibility = View.INVISIBLE

        auth = FirebaseAuth.getInstance()

        binding.btnGo.setOnClickListener {

            val email: String = binding.email.text.toString()
            val password: String = binding.password.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_LONG).show()
            } else {
                progressBar?.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), OnCompleteListener { task ->

                    progressBar?.visibility = View.INVISIBLE

                    if (task.isSuccessful) {
                        //Toast.makeText(requireContext(), "Successfully Logged In", Toast.LENGTH_LONG).show()

                        val intent = Intent(requireContext(), BottomNavActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()


                    } else {

                        Toast.makeText(requireContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }


        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.setOnTouchListener { v, event -> true }

        binding.backBtn.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.openRegisterFragment.setOnClickListener {
            val registerFragment = RegisterFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_sign_in, registerFragment)
            transaction?.commit()
        }
    }

}