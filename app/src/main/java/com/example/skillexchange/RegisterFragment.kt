package com.example.skillexchange

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.annotation.Nullable


class RegisterFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arrowButton: View? = view?.findViewById(R.id.arrow_btn_image_registerFrag)
        val signInBtn: View? = view?.findViewById(R.id.sign_in_tv_from_registerFrag)

        arrowButton?.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        signInBtn?.setOnClickListener {
            val signInrFragment = SignInFragment()

            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_register, signInrFragment)
            transaction?.commit()
        }
    }

}