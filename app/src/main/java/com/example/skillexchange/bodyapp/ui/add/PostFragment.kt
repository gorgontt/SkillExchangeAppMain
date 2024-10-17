package com.example.skillexchange.bodyapp.ui.add

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.databinding.FragmentAddBinding
import com.example.skillexchange.databinding.FragmentPostBinding

class PostFragment : Fragment() {


    private var _binding: FragmentPostBinding? = null
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
        val root: View = binding.root


        val editText: EditText = binding.editText
        val linearLayoutWords: LinearLayout = binding.linearLayoutWords

        binding.buttonAdd.setOnClickListener {
            val newSkills = editText.text.toString().trim()
            if (newSkills.isNotEmpty()) {
                addNewSkillToLayout(newSkills)
                postViewModel.addWord(newSkills)
                editText.text.clear()
            }
        }
        postViewModel.newSkills.observe(viewLifecycleOwner) { newSkills ->
            linearLayoutWords.removeAllViews() // Очищаем текущие представления
            newSkills.forEach { addNewSkillToLayout(it) } // Восстанавливаем слова
        }



        binding.buttonAddMySkills.setOnClickListener {
            val mySkill = binding.mySkillsEdT.text.toString().trim()
            if (mySkill.isNotEmpty()) {
                addMySkillsToLayout(mySkill)
                postViewModel.addMySkills(mySkill)
                editText.text.clear()
            }
        }
        postViewModel.mySkills.observe(viewLifecycleOwner) { mySkills ->
            binding.linearLayoutMySkills.removeAllViews() // Очищаем текущие представления
            mySkills.forEach { addMySkillsToLayout(it) } // Восстанавливаем слова
        }

        return root


    }

    private fun addNewSkillToLayout(newSkill: String) {
        val textView = TextView(requireContext()).apply {
            text = newSkill
            setPadding(20, 10, 20, 10)
            background = ContextCompat.getDrawable(requireContext(), R.drawable.button_corners_blue)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = 8
            }
            setTextColor(Color.WHITE)
            textSize = 16f
        }
        binding.linearLayoutWords.addView(textView)
    }

    private fun addMySkillsToLayout(mySkill: String) {
        val textView = TextView(requireContext()).apply {
            text = mySkill
            setPadding(20, 10, 20, 10)
            background = ContextCompat.getDrawable(requireContext(), R.drawable.button_corners_blue)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = 8
            }
            setTextColor(Color.WHITE)
            textSize = 16f
        }
        binding.linearLayoutMySkills.addView(textView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance() = PostFragment()
    }
}