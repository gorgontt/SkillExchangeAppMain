package com.example.skillexchange

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnSignIn = findViewById(R.id.btn_signIn)
        btnSignUp = findViewById(R.id.btn_signUp)
        

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSignIn.setOnClickListener {
            val signInFragment = SignInFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, signInFragment)
                .addToBackStack(null)
                .commit()
        }

        btnSignUp.setOnClickListener {
            val registerFragment = RegisterFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, registerFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}