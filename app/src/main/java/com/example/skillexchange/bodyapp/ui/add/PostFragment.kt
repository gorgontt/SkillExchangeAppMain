package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private lateinit var _binding: FragmentPostBinding
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addNewSkillsPostFragment.setOnClickListener {
            if (isAdded && !isDetached) {
                SkillsBottomSheetDialog.newInstance().show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
            }
        }

        return view
    }
    companion object {
        fun newInstance() = PostFragment()
    }
}