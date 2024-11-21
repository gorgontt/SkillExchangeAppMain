package com.example.skillexchange.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillexchange.models.UserRv
import com.example.skillexchange.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAppViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    val usersRepo = UsersRepo()

    //получаем всех пользователей
    fun getUsers() : LiveData<List<Users>>{
        return usersRepo.getUsers()
    }

    //get current user info
    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO ) {


    }
}