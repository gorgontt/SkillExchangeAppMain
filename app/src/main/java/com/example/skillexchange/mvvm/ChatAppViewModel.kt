package com.example.skillexchange.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillexchange.MyApplication
import com.example.skillexchange.SharedPrefs
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAppViewModel: ViewModel() {
    val name = MutableLiveData<String?>()
    val photoUrl = MutableLiveData<String?>()
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
//    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO ) {
//
//        val context = MyApplication.instance.applicationContext
//        firestore.collection("users").document(FirebaseUtils.getUiLoggedIn()).addSnapshotListener { value, error ->
//
//            if (value!!.exists() && value != null){
//                val users = value.toObject(Users::class.java)
//                name.value = users?.name!!
//                photoUrl.value = users?.photoUrl!!
//
//                val mysharedPrefs = SharedPrefs(context)
//                mysharedPrefs.setValue("name", users.name!!)
//            }
//        }
//
//
//    }

    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO ) {

        val context = MyApplication.instance.applicationContext
        firestore.collection("users").document(FirebaseUtils.getUiLoggedIn()).addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("ChatAppViewModel", "Error fetching user data", error)
                return@addSnapshotListener
            }

            if (value != null && value.exists()){
                val users = value.toObject(Users::class.java)
                if (users != null) {
                    name.postValue(users.name)
                    photoUrl.postValue(users.photoUrl)

                    val mysharedPrefs = SharedPrefs(context)
                    users.name?.let { mysharedPrefs.setValue("name", it) }
                } else {
                    Log.e("ChatAppViewModel", "User data is null")
                }
            } else {
                Log.e("ChatAppViewModel", "User document does not exist")
            }
        }
    }

    //send message
    fun sendMessage(sender: String?, receiver: String?, friendname: String?, friendimage: String?) = viewModelScope.launch(Dispatchers.IO) {

        if (sender == null || receiver == null || friendname == null || friendimage == null || message.value == null) {
            Log.e("ChatAppViewModel", "sendMessage: One of the parameters is null")
            return@launch
        }

        val context = MyApplication.instance.applicationContext

        val hashMap = hashMapOf<String, Any>(
            "sender" to sender,
            "receiver" to receiver,
            "message" to message.value!!,
            "time" to FirebaseUtils.getTime()
        )

        val uniqueId = listOf(sender, receiver).sorted().joinToString(separator = "")

        val friendnamesplit = friendname.split("\\s".toRegex())[0]
        val mySharedPrefs = SharedPrefs(context)
        mySharedPrefs.setValue("friendid", receiver)
        mySharedPrefs.setValue("chatroomid", uniqueId.toString())
        mySharedPrefs.setValue("friendname", friendnamesplit)
        mySharedPrefs.setValue("friendimage", friendimage)

        // отправка сообщения
        firestore.collection("messages").document(uniqueId.toString())
            .collection("chats").document(FirebaseUtils.getTime()).set(hashMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val hashMapForRecent = hashMapOf<String, Any>(
                        "friendid" to receiver,
                        "time" to FirebaseUtils.getTime(),
                        "sender" to FirebaseUtils.getUiLoggedIn(),
                        "message" to message.value!!,
                        "friendsimage" to friendimage,
                        "name" to friendname,
                        "person" to "you"
                    )

                    firestore.collection("Conversation${FirebaseUtils.getUiLoggedIn()}").document(receiver).set(hashMapForRecent)
                    firestore.collection("Conversation$receiver")
                        .document(FirebaseUtils.getUiLoggedIn())
                        .update("message", message.value!!, "time", FirebaseUtils.getTime(), "person", name.value!!)
                } else {
                    Log.e("ChatAppViewModel", "Failed to send message: ${task.exception?.message}")
                }
            }
    }
}