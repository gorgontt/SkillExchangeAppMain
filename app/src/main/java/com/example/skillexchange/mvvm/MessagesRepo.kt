package com.example.skillexchange.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Messages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessagesRepo {
    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(friendid: String) : LiveData<List<Messages>>{

        val messages = MutableLiveData<List<Messages>>()

        val uniqueid = listOf(FirebaseUtils.getUiLoggedIn(), friendid).sorted()
        uniqueid.joinToString(separator = "")

        firestore.collection("messages").document(uniqueid.toString()).collection("chats").orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener{value, error ->

                if (error != null){
                    return@addSnapshotListener
                }
                val messageList = mutableListOf<Messages>()

                if (!value!!.isEmpty){
                    value.documents.forEach {document ->

                        val messageModal = document.toObject(Messages::class.java)

                        if (messageModal!!.sender.equals(FirebaseUtils.getUiLoggedIn()) && messageModal.receiver.equals(friendid) ||
                            messageModal.sender.equals(friendid) && messageModal.receiver.equals(FirebaseUtils.getUiLoggedIn())) {

                            messageModal.let {
                                messageList.add(it!!)
                            }
                        }
                    }

                    messages.value = messageList
                }
            }
        return messages
    }
}