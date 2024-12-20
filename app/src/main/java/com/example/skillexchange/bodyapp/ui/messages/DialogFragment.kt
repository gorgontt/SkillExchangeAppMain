package com.example.skillexchange.bodyapp.ui.messages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.skillexchange.R
import com.example.skillexchange.adapter.MessageAdapter
import com.example.skillexchange.databinding.FragmentDialogBinding
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Messages
import com.example.skillexchange.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth

class DialogFragment : Fragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var args: DialogFragmentArgs
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var toolBar: Toolbar
    private lateinit var tvUserName: TextView
    private lateinit var tvStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = DialogFragmentArgs.fromBundle(requireArguments())
        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)

        binding.backBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_dialogFragment_to_navigation_notifications)
        }

        toolBar = view.findViewById(R.id.toolbar)
        tvStatus = view.findViewById(R.id.status)
        tvUserName = view.findViewById(R.id.userNameDialogFrag)

        Glide.with(requireContext()).load(args.users.photoUrl).into(binding.userPhoto)
        tvStatus.text = args.users.status
        tvUserName.text = args.users.name

        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        chatAppViewModel.getUsers().observe(viewLifecycleOwner, Observer { userList ->
            if (userList != null) {

            }
        })

//        binding.sendBtn.setOnClickListener {
//            chatAppViewModel.sendMessage(FirebaseUtils.getUiLoggedIn(), args.users.userId!!, args.users.name!!, args.users.photoUrl!!)
//        }

//        binding.sendBtn.setOnClickListener {
//            val senderId = FirebaseUtils.getUiLoggedIn()
//            val receiverId = args.users.userId
//            val friendName = args.users.name
//            val friendImage = args.users.photoUrl
//
//            if (receiverId.isNullOrEmpty() || friendName.isNullOrEmpty() || friendImage.isNullOrEmpty()) {
//                Log.e("DialogFragment", "One of the user parameters is null or empty")
//                return@setOnClickListener
//            }
//
//            chatAppViewModel.sendMessage(senderId, receiverId, friendName, friendImage)
//        }

        binding.edTMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                chatAppViewModel.message.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.sendBtn.setOnClickListener {
            val messageText = binding.edTMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val senderId = FirebaseUtils.getUiLoggedIn()
                val receiverId = args.users.userId
                val friendName = args.users.name
                val friendImage = args.users.photoUrl

                if (!senderId.isNullOrEmpty() && !receiverId.isNullOrEmpty() &&
                    !friendName.isNullOrEmpty() && !friendImage.isNullOrEmpty()) {

                    chatAppViewModel.message.value = messageText
                    chatAppViewModel.sendMessage(senderId, receiverId, friendName, friendImage)
                    binding.edTMessage.setText("") // Clear the input field
                } else {
                    Toast.makeText(requireContext(),
                        "Unable to send message - missing user information",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        chatAppViewModel.getMessages(args.users.userId!!).observe(viewLifecycleOwner, Observer { messages ->
            if (messages != null) {
                initRecyclerView(messages)
            } else {
                Log.e("DialogFragment", "No messages available")
            }
        })

//        chatAppViewModel.getMessages(args.users.userId!!).observe(viewLifecycleOwner, Observer {
//
//            initRecyclerView(it)
//
//        })

    }


    private fun initRecyclerView(messages: List<Messages>?) {
        if (!::messageAdapter.isInitialized) {
            messageAdapter = MessageAdapter()
            binding.rvMessages.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    stackFromEnd = true
                }
                adapter = messageAdapter
            }
        }

        messages?.let {
            messageAdapter.setMessageList(it)
            binding.rvMessages.scrollToPosition(it.size - 1)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}