package com.example.skillexchange.bodyapp.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.adapter.NewSkillsBottomSheetAdapter
import com.example.skillexchange.databinding.FragmentFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterFragment : Fragment() {

    private lateinit var _binding: FragmentFilterBinding
    private lateinit var dialogNewSkills: BottomSheetDialog
    private lateinit var newSkillsAdapter: NewSkillsBottomSheetAdapter
    private lateinit var newSkillsRV: RecyclerView
    private val newSkillsList = ArrayList<String>()
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

        for (i in 1..10){
            newSkillsList.add("item $i")
        }

        binding.tvOpenAllNewSkills.setOnClickListener {
            bottomSheetDialogNewSkills()
        }

        return view
    }

    private fun bottomSheetDialogNewSkills(){
        val dialogView = layoutInflater.inflate(R.layout.bottom_new_skills_filter, null)
        dialogNewSkills = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme) // Используйте стандартный стиль
        dialogNewSkills.setContentView(dialogView)

        newSkillsRV = dialogView.findViewById(R.id.bottomSheet_RV_new_skills)

        newSkillsAdapter = NewSkillsBottomSheetAdapter(newSkillsList)
        newSkillsRV.adapter = newSkillsAdapter

        // Устанавливаем параметр высоты для диалога
        val bottomSheet = dialogNewSkills.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val layoutParams = bottomSheet?.layoutParams
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT // Устанавливаем обертку
        bottomSheet?.layoutParams = layoutParams

        dialogNewSkills.show()
    }

}