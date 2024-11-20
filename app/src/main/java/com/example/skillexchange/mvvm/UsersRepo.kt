package com.example.skillexchange.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Users
import com.google.firebase.firestore.FirebaseFirestore

class UsersRepo {

    private var firestore = FirebaseFirestore.getInstance()

    //список пользователей в реальном времени
    fun getUsers() : LiveData<List<Users>>{

        val users = MutableLiveData<List<Users>>()

        firestore.collection("users").addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Log.e("UsersRepo", "Error fetching users", exception)
                return@addSnapshotListener
            }
            val userList = mutableListOf<Users>()
            snapshot?.documents?.forEach { document ->
                val user = document.toObject(Users::class.java)

                // Логируем полученного пользователя
                Log.d("UsersRepo", "Fetched user: $user")

                // Добавляем в список пользователей, кроме текущего
                if (user!!.userId != FirebaseUtils.getUiLoggedIn()) {
                    userList.add(user)
                }
            }
            users.value = userList

            }
        return users
    }
}