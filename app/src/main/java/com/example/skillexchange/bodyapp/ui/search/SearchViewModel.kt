package com.example.skillexchange.bodyapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skillexchange.interfaces.UserRv
import com.google.firebase.firestore.CollectionReference

class SearchViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<UserRv>>()
    val userList: LiveData<List<UserRv>> get() = _userList

    fun updateUserList(userCollection: CollectionReference) {
        userCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val userList = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(UserRv::class.java) // Преобразование документа в объект UserRv
                }
                _userList.value = userList // Обновление LiveData
            }
            .addOnFailureListener { exception ->
                Log.w("SearchViewModel", "Ошибка получения пользователей: ", exception)
                _userList.value = emptyList() // Или другое значение по умолчанию в случае ошибки
            }
    }
}