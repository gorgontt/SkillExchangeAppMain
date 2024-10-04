package com.example.skillexchange


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import javax.annotation.Nullable


class SignInFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arrowButton: View? = view?.findViewById(R.id.arrow_btn_image)
        val registerTV: View? = view?.findViewById(R.id.register_tv_from_signIn)

        arrowButton?.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        registerTV?.setOnClickListener {

            val registerFragment = RegisterFragment()


            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_sign_in, registerFragment)
//            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }


}