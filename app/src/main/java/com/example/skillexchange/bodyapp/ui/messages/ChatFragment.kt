package com.example.skillexchange.bodyapp.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.adapter.ChatRoomsAdapter
import com.example.skillexchange.databinding.FragmentChatBinding
import com.example.skillexchange.mvvm.ChatAppViewModel

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    lateinit var chatRoomsRv : RecyclerView
    lateinit var chatRoomsAdapter: ChatRoomsAdapter
    lateinit var userViewModel: ChatAppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        chatRoomsAdapter = ChatRoomsAdapter()
        chatRoomsRv = view.findViewById(R.id.chatRooms_rv)
        val layoutManagerUsers = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        chatRoomsRv.layoutManager = layoutManagerUsers
        chatRoomsRv.adapter = chatRoomsAdapter // Установите адаптер здесь

        userViewModel.getUsers().observe(viewLifecycleOwner, Observer { userList ->
            // Убедитесь, что userList не null
            if (userList != null) {
                chatRoomsAdapter.setUserList(userList)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}