package com.example.skillexchange.bodyapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.adapter.SearchFragmentAdapter
import com.example.skillexchange.databinding.FragmentSearchBinding
import com.example.skillexchange.interfaces.UserRv
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: SearchFragmentAdapter
    private val userList: MutableList<UserRv> = mutableListOf()
    private var db = Firebase.firestore
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        userAdapter = SearchFragmentAdapter(userList)
        binding.rvPostSearchFragment.adapter = userAdapter

        binding.filterFab.setOnClickListener {
            openNewFragment()
        }

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            fetchUserData(currentUser.uid)
        }


//        val textView: TextView = binding.textHome
//        searchViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    private fun openNewFragment() {
        val newFragment = FilterFragment()
        val fragmentManager = requireActivity().supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(R.id.search_frame, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun fetchUserData(userId: String) {
        // Получаем данные пользователя из Firestore
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val name = document.getString("name") ?: "Unknown"
                    val age = document.getString("age") ?: "Unknown"

                    // Создаем объект UserRv и добавляем его в список
                    val currentUser = UserRv(name, age)
                    userList.add(currentUser)

                    // Уведомляем адаптер об изменениях в данных
                    userAdapter.notifyDataSetChanged()
                } else {
                    Log.d("SearchFragment", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("SearchFragment", "Get failed with ", exception)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}