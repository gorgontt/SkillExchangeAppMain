package com.example.skillexchange.bodyapp.ui.messages

import android.os.Bundle
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
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Message
import com.example.skillexchange.models.Users
import com.example.skillexchange.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding
    lateinit var adapter: MessageAdapter
    lateinit var auth: FirebaseAuth
    private lateinit var users: Users
    private lateinit var chatAppViewModel: ChatAppViewModel
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

        chatAppViewModel = ChatAppViewModel(this)
        users = Users()

        auth = Firebase.auth
        val username = intent.getStringExtra("username")
        val photoUrl = intent.getStringExtra("photoUrl")
        binding.userName.setText(username)
        Glide.with(this).load(photoUrl).into(binding.userPhoto)

        val db = Firebase.database
        val myRef = db.getReference("messages")

//        binding.sendBtn.setOnClickListener {
//            chatAppViewModel.sendMessage(FirebaseUtils.getUiLoggedIn(), users.userId!!, users.name!!, users.photoUrl!!)
//        }

        binding.sendBtn.setOnClickListener {
            Log.d("DialogActivity", "sendButton clicked")
            chatAppViewModel.sendMessage(FirebaseUtils.getUiLoggedIn(), users.userId!!, users.name!!, users.photoUrl!!)
        }

        if (users.userId != null && users.name != null && users.photoUrl != null) {
            chatAppViewModel.sendMessage(FirebaseUtils.getUiLoggedIn(),
                users.userId!!, users.name!!, users.photoUrl!!
            )
        } else {
            Log.e("DialogActivity", "User properties are null")
        }

//        binding.sendBtn.setOnClickListener {
//            myRef.child(myRef.push().key ?: "notNull").setValue(
//                Message(
//                    auth.currentUser?.displayName,
//                    binding.edTMessage.text.toString()
//                )
//            )
//        }
//        onChangeListener(myRef)
//        initRcView()

    }

//    private fun initRcView() = with(binding){
//        adapter = MessageAdapter()
//        rvMessages.layoutManager = LinearLayoutManager(this@DialogActivity)
//        rvMessages.adapter = adapter
//    }

//    private fun onChangeListener(dRef: DatabaseReference){
//        dRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = ArrayList<Message>()
//                for (s in snapshot.children){
//
//                    val user = s.getValue(Message::class.java)
//                    if (user!= null)list.add(user)
//
//                }
//                adapter.submitList(list)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//    }


}