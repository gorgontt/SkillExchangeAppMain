package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillexchange.adapter.ListItem
import com.example.skillexchange.adapter.SkillsAdapter
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentPostBinding
import com.example.skillexchange.interfaces.OnSkillsSelectedListener

class PostFragment : Fragment(), OnSkillsSelectedListener {

    private lateinit var _binding: FragmentPostBinding
    private val binding get() = _binding!!
    private lateinit var postViewModel: PostViewModel
    private lateinit var skillsAdapter: SkillsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val view = binding.root

        skillsAdapter = SkillsAdapter(mutableListOf())  // Измените на MutableList
//        binding.skillsRecyclerviewPostFragment.layoutManager = LinearLayoutManager(context)
        binding.skillsRecyclerviewPostFragment.adapter = skillsAdapter

        binding.addNewSkillsPostFragment.setOnClickListener {
            if (isAdded && !isDetached) {
                val skillsBottomSheet = SkillsBottomSheetDialog()
                skillsBottomSheet.setTargetFragment(this, 1)
                skillsBottomSheet.show(requireActivity().supportFragmentManager, "SkillsBottomSheet")
            }
        }

        return view
    }

    override fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>) {
        // Получаем текущие навыки и добавляем новые
        val currentSkills = skillsAdapter.items.toMutableList()
        currentSkills.addAll(selectedSkills)
        skillsAdapter.updateSkills(currentSkills)  // Обновляем адаптер
    }

    companion object {
        fun newInstance() = PostFragment()
    }
}
