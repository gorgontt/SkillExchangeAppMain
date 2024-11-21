package com.example.skillexchange.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillexchange.MyApplication
import com.example.skillexchange.SharedPrefs
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.UserRv
import com.example.skillexchange.models.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAppViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    private val firestore = FirebaseFirestore.getInstance()

    val usersRepo = UsersRepo()

    init {
        getCurrentUser()
    }

    //получаем всех пользователей
    fun getUsers() : LiveData<List<Users>>{
        return usersRepo.getUsers()
    }

    //get current user info
    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO ) {

        val context = MyApplication.instance.applicationContext
        firestore.collection("users").document(FirebaseUtils.getUiLoggedIn()).addSnapshotListener { value, error ->

            if (value!!.exists() && value != null){
                val users = value.toObject(Users::class.java)
                name.value = users?.name!!
                photoUrl.value = users?.photoUrl!!

                val mysharedPrefs = SharedPrefs(context)
                mysharedPrefs.setValue("name", users.name!!)
            }
        }


    }
}