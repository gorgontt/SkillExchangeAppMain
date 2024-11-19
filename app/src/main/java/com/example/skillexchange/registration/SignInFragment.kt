package com.example.skillexchange.registration


import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.skillexchange.MainActivity
import com.example.skillexchange.R
import com.example.skillexchange.bodyapp.BottomNavActivity
import com.example.skillexchange.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.annotation.Nullable


class SignInFragment : Fragment() {

    private lateinit var _binding: FragmentSignInBinding
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var progressBar: ProgressBar? = null
    lateinit var launcher: ActivityResultLauncher<Intent>



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

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            }catch (e: ApiException){
                Log.d("MyLog", "Api exception")
            }
        }

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
                        val intent = Intent(requireContext(), BottomNavActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()


                    } else {

                        Toast.makeText(requireContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        binding.googleSignInBtn.setOnClickListener {
            googleSignIn()
        }
        checkAuthState()


        return view
    }

    private fun getClientGoogle(): GoogleSignInClient{

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return  GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun googleSignIn(){
        val signInClient = getClientGoogle()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                Log.d("MyLog", "google sign in done")
                checkAuthState()
            } else {
                Log.d("MyLog", "google sign in error")
            }
        }
    }

    private fun checkAuthState(){
        if (auth.currentUser != null){
            val i = Intent(requireContext(), BottomNavActivity::class.java)
            startActivity(i)
        }
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