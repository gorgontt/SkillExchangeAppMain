package com.example.skillexchange.bodyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.adapter.MySkillsAdapter
import com.example.skillexchange.adapter.SelectedSkillsAdapter
import com.example.skillexchange.bodyapp.ui.search.SearchFragment
import com.example.skillexchange.bottomsheetdialog.SkillsBottomSheetDialog
import com.example.skillexchange.databinding.FragmentAddBinding
import com.example.skillexchange.interfaces.OnSkillsSelectedListener
import com.example.skillexchange.models.ListItem
import com.example.skillexchange.models.Skill
import com.example.skillexchange.models.UserRv
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddFragment : BottomSheetDialogFragment(), OnSkillsSelectedListener {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addViewModel: AddViewModel

    private lateinit var skillsAdapter: SelectedSkillsAdapter
    private lateinit var mySkillsAdapter: MySkillsAdapter
    private val mySkillsList: MutableList<Skill> = mutableListOf()
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var etDescription: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet = view.parent as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.createPostBottomSheetDialog.setOnClickListener {
//            dismiss()
////            val newFragment = PostFragment()
////            val fragmentManager = requireActivity().supportFragmentManager
////            fragmentManager.beginTransaction()
////                .replace(R.id.fragment_container_nav, newFragment)
////                .addToBackStack(null)
////                .commit()
//            val bottomSheet = PostFragment()
//            bottomSheet.setTargetFragment(this, 1)
//            bottomSheet.show(childFragmentManager, "BottomSheet")
//        }

        skillsAdapter = SelectedSkillsAdapter(mutableListOf())
        binding.skillsRecyclerviewPostFragment.adapter = skillsAdapter
        etDescription = binding.descriptionPostFragment

        binding.addNewSkillsPostFragment.setOnClickListener {
            if (isAdded && !isDetached) {
                val skillsBottomSheet = SkillsBottomSheetDialog()
                skillsBottomSheet.setTargetFragment(this, 1)
                skillsBottomSheet.show(parentFragmentManager, "SkillsBottomSheet")
            }
        }

        mySkillsAdapter = MySkillsAdapter(mySkillsList)
        binding.mySkillsRecyclerviewPostFragment.adapter = mySkillsAdapter
        loadUserSkills()

        binding.publishButton.setOnClickListener {
//            val transaction = fragmentManager?.beginTransaction()
//            transaction?.replace(R.id.post_frame, SearchFragment())
//            transaction?.commit()
            dismiss()

            loadPost()
        }

        return root
    }

    override fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>) {
        val currentSkills = skillsAdapter.items.toMutableList()
        currentSkills.addAll(selectedSkills)
        skillsAdapter.updateSkills(currentSkills)
    }

    companion object {
        fun newInstance() = PostFragment()
    }

    private fun loadUserSkills(){
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val skills = document.get("skills") as? List<String>
                    if (skills != null) {
                        mySkillsList.clear()
                        mySkillsList.addAll(skills.map { Skill(it) })
                        mySkillsAdapter.notifyDataSetChanged()
                    }
                }.addOnFailureListener {
                    Toast.makeText(activity, "Ошибка загрузки навыков", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loadPost() {
        val description = binding.descriptionPostFragment.text.toString()

        if (description.isNotEmpty()) {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                db.collection("users").document(userId).get()
                    .addOnSuccessListener { userDocument ->
                        if (userDocument.exists()) {
                            val name = userDocument.getString("name") ?: "Unknown"
                            val age = userDocument.getString("age") ?: "Unknown"
                            val userPhoto = userDocument.getString("photoUrl") ?: "Unknown"

                            val selectedSkills = skillsAdapter.items.filterIsInstance<ListItem.TextItem>().map { it.text }
                            val mySkills = mySkillsAdapter.skills.filterIsInstance<Skill>().map { it.name }

                            val post = UserRv(
                                description = description,
                                name = name,
                                age = age,
                                newSkills = selectedSkills,
                                mySkills = mySkills,
                                photoUrl = userPhoto,
                                userId = Firebase.auth.currentUser!!.uid
                            )

                            val postDocument = db.collection("post").document()
                            postDocument.set(post)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Пост успешно опубликован", Toast.LENGTH_SHORT).show()
                                    dismiss()
//                                    val newFragment = SearchFragment()
//                                    val fragmentManager = requireActivity().supportFragmentManager
//                                    fragmentManager.beginTransaction()
//                                        .replace(R.id.search_frame, newFragment)
//                                        .addToBackStack(null)
//                                        .commit()

                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(requireContext(), "Ошибка при публикации поста", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Ошибка при загрузке данных пользователя", Toast.LENGTH_LONG).show()
                    }
            }
        } else {
            Toast.makeText(requireContext(), "Описание не может быть пустым", Toast.LENGTH_LONG).show()
        }
    }
    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}