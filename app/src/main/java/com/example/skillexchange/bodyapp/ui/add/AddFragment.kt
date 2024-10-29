package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.createPostBottomSheetDialog.setOnClickListener {
            dismiss()
            val newFragment = PostFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.search_frame, newFragment)
                .addToBackStack(null)
                .commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}