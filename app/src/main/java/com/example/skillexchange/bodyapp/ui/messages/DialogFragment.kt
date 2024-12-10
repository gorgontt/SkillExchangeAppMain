package com.example.skillexchange.bodyapp.ui.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.adapter.MessageAdapter
import com.example.skillexchange.databinding.FragmentDialogBinding
import com.example.skillexchange.mvvm.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth

class DialogFragment : Fragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var args: DialogFragmentArgs
    private lateinit var adapter: MessageAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var chatAppViewModel: ChatAppViewModel

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

        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        chatAppViewModel.getUsers().observe(viewLifecycleOwner, Observer { userList ->
            if (userList != null) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}