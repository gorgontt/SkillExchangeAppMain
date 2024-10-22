package com.example.skillexchange.bodyapp.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.adapter.ListItem
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

//        val words = listOf(
//            ListItem.HeaderItem("Header 1"),
//            ListItem.TextItem("Text Item 1"),
//            ListItem.TextItem("Text Item 2"),
//            ListItem.HeaderItem("Header 2"),
//            ListItem.TextItem("Text Item 3"),
//            ListItem.TextItem("Text Item 4"),
//            ListItem.HeaderItem("Header 3")
//        )
//        newSkillsList.addAll(words)

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
        val words = listOf(
            ListItem.HeaderItem("Header 1"),
            ListItem.TextItem("Text Item 1"),
            ListItem.TextItem("Text Item 2"),
            ListItem.HeaderItem("Header 2"),
            ListItem.TextItem("Text Item 3"),
            ListItem.TextItem("Text Item 4"),
            ListItem.HeaderItem("Header 3")
        )
        newSkillsAdapter = NewSkillsBottomSheetAdapter(words)
        newSkillsRV.layoutManager = LinearLayoutManager(context)
        newSkillsRV.adapter = newSkillsAdapter


//        fun onListItemClick(val item: String){
//            Toast.makeText(context, "Clicked $item", Toast.LENGTH_SHORT).show()
//        }

        // Устанавливаем параметр высоты для диалога
//        val bottomSheet = dialogNewSkills.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        val layoutParams = bottomSheet?.layoutParams
//        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT // Устанавливаем обертку
//        bottomSheet?.layoutParams = layoutParams

        dialogNewSkills.show()
    }
}