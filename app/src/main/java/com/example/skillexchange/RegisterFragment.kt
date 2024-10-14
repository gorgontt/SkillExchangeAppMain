package com.example.skillexchange

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.skillexchange.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import javax.annotation.Nullable


class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private var progressBar: ProgressBar? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()

        progressBar = view.findViewById<ProgressBar>(R.id.progress_Bar_register)

        progressBar?.visibility = View.INVISIBLE


        binding.goBtn.setOnClickListener {
            val email: String = binding.email.text.toString()
            val password: String = binding.password.text.toString()
            val repeatPass: String = binding.repeatPassword.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPass)) {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT)
                    .show()

            }else if (password != repeatPass){
                Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()

            } else if (!validPassword(password)) {
                Toast.makeText(requireContext(), "Пароль должен содержать не менее 6 символов, включая буквы и цифры", Toast.LENGTH_SHORT).show()

            } else {
                progressBar?.visibility = View.VISIBLE

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->

                        progressBar?.visibility = View.INVISIBLE

                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Successfully Registered",
                                Toast.LENGTH_LONG
                            ).show()
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.frame_register,
                                    RegisterBioFragment()
                                )
                                .addToBackStack(null)
                                .commit()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Не получилось зарегистрироваться",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }
        progressBar?.visibility = View.INVISIBLE

        binding.backBtn.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.openSignInFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_register, SignInFragment())
                .commit()
        }


        return view
    }

    private fun validPassword(password: String): Boolean{
        if (password.length < 6){ return false }
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return hasLetter && hasDigit
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.root.setOnTouchListener { v, event -> true }

        binding.backBtn.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.openSignInFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_register, SignInFragment())
                .commit()
        }

    }

}