package com.example.skillexchange.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Messages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
class MessagesRepo {
    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(friendid: String): LiveData<List<Messages>> {
        val messages = MutableLiveData<List<Messages>>()

        // Create unique ID correctly
        val uniqueId = listOf(FirebaseUtils.getUiLoggedIn(), friendid)
            .sorted()
            .joinToString(separator = "")

        firestore.collection("messages")
            .document(uniqueId)
            .collection("chats")
            .orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("MessagesRepo", "Error fetching messages: ${error.message}")
                    return@addSnapshotListener
                }

                val messageList = mutableListOf<Messages>()

                if (value != null && !value.isEmpty) {
                    for (document in value.documents) {
                        val messageModal = document.toObject(Messages::class.java)
                        if (messageModal != null) {
                            if ((messageModal.sender == FirebaseUtils.getUiLoggedIn() && messageModal.receiver == friendid) ||
                                (messageModal.sender == friendid && messageModal.receiver == FirebaseUtils.getUiLoggedIn())) {
                                messageList.add(messageModal)
                            }
                        }
                    }
                    messages.value = messageList
                } else {
                    messages.value = emptyList()
                    Log.d("MessagesRepo", "No messages found")
                }
            }
        return messages
    }
}