package com.example.skillexchange.bodyapp.ui.messages

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skillexchange.R
import com.example.skillexchange.adapter.MessageAdapter
import com.example.skillexchange.databinding.ActivityDialogBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val username = intent.getStringExtra("username")
        val photoUrl = intent.getStringExtra("photoUrl")
        binding.userName.setText(username)
        Glide.with(this).load(photoUrl).into(binding.userPhoto)

        val db = Firebase.database
        val myRef = db.getReference("messages")

        binding.sendBtn.setOnClickListener {
            myRef.setValue(binding.edTMessage.text.toString())
        }
        onChangeListener(myRef)

    }

    private fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    messageText.append("\n")
                    messageText.append(snapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}