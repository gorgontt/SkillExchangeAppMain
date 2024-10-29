package com.example.skillexchange.bodyapp.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private lateinit var _binding: FragmentFilterBinding
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FilterFragment()
    }

    private val viewModel: FilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvOpenAllNewSkills.setOnClickListener {
            SkillsBottomSheetDialog.newInstance().show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
        }

        binding.mySkillsFilterFragment.setOnClickListener {
            SkillsBottomSheetDialog.newInstance().show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
        }



        return view
    }

//    private fun bottomSheetDialogNewSkills(){
//        val dialogView = layoutInflater.inflate(R.layout.bottom_new_skills_filter, null)
//        dialogNewSkills = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme) // Используйте стандартный стиль
//        //dialogNewSkills = BottomSheetDialog(requireContext()) // Используйте стандартный стиль
//        dialogNewSkills.setContentView(dialogView)
//
//        newSkillsRV = dialogView.findViewById(R.id.bottomSheet_RV_new_skills)
//        val words = newSkillsRepository.getNewSkillsWords()
//        newSkillsAdapter = NewSkillsBottomSheetAdapter(words)
//        newSkillsRV.layoutManager = LinearLayoutManager(context)
//        newSkillsRV.adapter = newSkillsAdapter
//
//        dialogNewSkills.show()
//    }
}