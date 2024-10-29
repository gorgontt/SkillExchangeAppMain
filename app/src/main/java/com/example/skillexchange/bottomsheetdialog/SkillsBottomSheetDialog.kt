package com.example.skillexchange.bottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.adapter.NewSkillsBottomSheetAdapter
import com.example.skillexchange.adapter.NewSkillsRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SkillsBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var newSkillsAdapter: NewSkillsBottomSheetAdapter
    private lateinit var newSkillsRV: RecyclerView
    private val newSkillsRepository = NewSkillsRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialogView = inflater.inflate(R.layout.bottom_new_skills_filter, container, false)

        newSkillsRV = dialogView.findViewById(R.id.bottomSheet_RV_new_skills)
        val words = newSkillsRepository.getNewSkillsWords()
        newSkillsAdapter = NewSkillsBottomSheetAdapter(words)
        newSkillsRV.layoutManager = LinearLayoutManager(context)
        newSkillsRV.adapter = newSkillsAdapter

        return dialogView
    }

    companion object {
        fun newInstance(): SkillsBottomSheetDialog {
            return SkillsBottomSheetDialog()
        }
    }
}