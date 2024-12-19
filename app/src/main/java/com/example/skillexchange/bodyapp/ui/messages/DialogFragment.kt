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

        Glide.with(requireContext()).load(args.users.photoUrl).into(binding.userPhoto)
        tvStatus.setText(args.users.status)
        binding.userName.setText(args.users.name)

        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        chatAppViewModel.getUsers().observe(viewLifecycleOwner, Observer { userList ->
            if (userList != null) {

            }
        })

//        binding.sendBtn.setOnClickListener {
//            chatAppViewModel.sendMessage(FirebaseUtils.getUiLoggedIn(), args.users.userId!!, args.users.name!!, args.users.photoUrl!!)
//        }
        binding.sendBtn.setOnClickListener {
            val sender = FirebaseUtils.getUiLoggedIn()
            val receiver = args.users.userId
            val friendname = args.users.name
            val friendimage = args.users.photoUrl

            if (receiver != null && friendname != null && friendimage != null) {
                chatAppViewModel.sendMessage(sender, receiver, friendname, friendimage)
            } else {
                Log.e("DialogFragment", "One of the necessary parameters is null!")
            }
        }
        chatAppViewModel.getMessages(args.users.userId!!).observe(viewLifecycleOwner, Observer {

            initRecyclerView(it)

        })


        binding.edTMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                chatAppViewModel.message.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

//        binding.sendBtn.setOnClickListener {
//            val senderId = FirebaseUtils.getUiLoggedIn()
//            val receiverId = args.users.userId
//            val friendName = args.users.name
//            val friendImage = args.users.photoUrl
//            val messageText = chatAppViewModel.message.value
//
//            if (receiverId != null) {
//                if (!senderId.isNullOrEmpty() && !friendName.isNullOrEmpty() && !friendImage.isNullOrEmpty() && !messageText.isNullOrEmpty()) {
//                    chatAppViewModel.sendMessage(senderId, receiverId, friendName, friendImage)
//
//                    // Удаляем:
//                    // chatAppViewModel.getMessages(receiverId).observe(viewLifecycleOwner, Observer { messages ->
//                    //    initRecyclerView(messages)
//                    // });
//
//                    binding.edTMessage.text.clear()
//                } else {
//                    Toast.makeText(
//                        requireContext(),
//                        "One of the message parameters is null or empty",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Receiver ID is null, can't send message",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }

//        binding.sendBtn.setOnClickListener {
//            val senderId = FirebaseUtils.getUiLoggedIn()
//            val receiverId = args.users.userId
//            val friendName = args.users.name
//            val friendImage = args.users.photoUrl
//            val messageText = chatAppViewModel.message.value
//
//            Log.d("DialogFragment", "senderId: $senderId, receiverId: $receiverId, friendName: $friendName, friendImage: $friendImage, messageText: $messageText")
//
//            if (receiverId != null) {
//                if (!senderId.isNullOrEmpty() && !friendName.isNullOrEmpty() && !friendImage.isNullOrEmpty() && !messageText.isNullOrEmpty()) {
//                    chatAppViewModel.sendMessage(senderId, receiverId, friendName, friendImage)
//                    binding.edTMessage.text.clear()
//                } else {
//                    Toast.makeText(
//                        requireContext(),
//                        "One of the message parameters is null or empty",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Receiver ID is null, can't send message",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }




    }

    private fun initRecyclerView(it: List<Messages>) {

        messageAdapter = MessageAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.rvMessages.layoutManager = layoutManager
        layoutManager.stackFromEnd = true

        messageAdapter.setMessageList(it)
        messageAdapter.notifyDataSetChanged()
        binding.rvMessages.adapter = messageAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}