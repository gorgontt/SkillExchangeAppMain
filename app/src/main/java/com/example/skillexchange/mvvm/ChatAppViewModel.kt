package com.example.skillexchange.mvvm

import android.content.Context
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

class ChatAppViewModel(val context: Context): ViewModel() {
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

    fun sendMessage(sender: String, receiver: String, friendName: String, friendImage: String) = viewModelScope.launch(Dispatchers.IO) {

        val context = MyApplication.instance.applicationContext

        val hashMap = hashMapOf<String, Any>(
            "sender" to sender,
            "receiver" to receiver,
            "message" to message.value!!,
            "time" to FirebaseUtils.getTime()
        )

        val uniqueId = listOf(sender, receiver).sorted()
        uniqueId.joinToString(separator = "")

        val friendNameSplit = friendName.split("\\s".toRegex())[0]
        val mySharedPrefs = SharedPrefs(context)

        mySharedPrefs.setValue("friendId", receiver)
        mySharedPrefs.setValue("charRoomId", uniqueId.toString())
        mySharedPrefs.setValue("friendName", friendNameSplit)
        mySharedPrefs.setValue("friendImage", friendImage)

        //sending message
        firestore.collection("messages").document(uniqueId.toString())
            .collection("chats").document(FirebaseUtils.getTime()).set(hashMap).addOnCompleteListener { task->

                val hashMapForRecent = hashMapOf<String, Any>(
                    "friendId" to receiver,
                    "time" to FirebaseUtils.getTime(),
                    "sender" to FirebaseUtils.getUiLoggedIn(),
                    "message" to message.value!!,
                    "friendsImage" to friendImage,
                    "name" to friendName,
                    "person" to "you"

                )

                firestore.collection("Conversation${FirebaseUtils.getUiLoggedIn()}").document(receiver).set(hashMapForRecent)
                firestore.collection("Conversation${receiver}")
                    .document(FirebaseUtils.getUiLoggedIn())
                    .update("message", message.value!!, "time", FirebaseUtils.getTime(), "person", name.value!!)

            }

        //message.value = ""
    }
}