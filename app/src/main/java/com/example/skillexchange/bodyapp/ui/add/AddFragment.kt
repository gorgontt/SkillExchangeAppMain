package com.example.skillexchange.bodyapp.ui.add

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editText: EditText = binding.editText
        val linearLayoutWords: LinearLayout = binding.linearLayoutWords

        binding.buttonAdd.setOnClickListener {
            val word = editText.text.toString().trim()
            if (word.isNotEmpty()) {
                addWordToLayout(word)
                addViewModel.addWord(word)
                editText.text.clear()
            }
        }
        addViewModel.words.observe(viewLifecycleOwner) { words ->
            linearLayoutWords.removeAllViews() // Очищаем текущие представления
            words.forEach { addWordToLayout(it) } // Восстанавливаем слова
        }




        binding.buttonAddMySkills.setOnClickListener {
            val mySkill = binding.mySkillsEdT.text.toString().trim()
            if (mySkill.isNotEmpty()) {
                addMySkillsToLayout(mySkill)
                addViewModel.addMySkills(mySkill)
                editText.text.clear()
            }
        }
        addViewModel.mySkills.observe(viewLifecycleOwner) { mySkills ->
            binding.linearLayoutMySkills.removeAllViews() // Очищаем текущие представления
            mySkills.forEach { addMySkillsToLayout(it) } // Восстанавливаем слова
        }

        return root
    }

    private fun addWordToLayout(word: String) {
        val textView = TextView(requireContext()).apply {
            text = word
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
}