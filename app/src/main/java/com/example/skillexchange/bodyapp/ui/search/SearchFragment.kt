package com.example.skillexchange.bodyapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skillexchange.R
import com.example.skillexchange.adapter.PostAdapter
import com.example.skillexchange.databinding.FragmentSearchBinding
import com.example.skillexchange.models.UserRv
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment(), PostAdapter.PostItemListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: PostAdapter
    private val userList = ArrayList<UserRv> ()
    //private val userList: MutableList<UserRv> = mutableListOf()
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
        userAdapter = PostAdapter(this, requireContext(), userList)
        binding.rvPostSearchFragment.adapter = userAdapter

        binding.filterFab.setOnClickListener {
            openNewFragment()
        }

        searchViewModel.userList.observe(viewLifecycleOwner, Observer { users ->
            userList.clear()
            userList.addAll(users)
            userAdapter.notifyDataSetChanged()
        })

        Firebase.firestore.collection("post").get().addOnSuccessListener {

            var tempList = ArrayList<UserRv>()
            userList.clear()

            for (i in it.documents){

                var post: UserRv = i.toObject<UserRv>()!!
                tempList.add(post)
            }

            for (i in tempList.indices.reversed()) {
                userList.add(0, tempList[i])
            }

            //postList.addAll(tempList)
            userAdapter.notifyDataSetChanged()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(user: UserRv) {
        val intent = Intent(requireContext(), ContentActivity::class.java)
        intent.putExtra("postItem", user)
        activity?.startActivity(intent)
    }
}