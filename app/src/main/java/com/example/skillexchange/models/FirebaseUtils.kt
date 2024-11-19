package com.example.skillexchange.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUtils {

    fun currentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }
    fun currentUserDetails(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId())
    }

    fun allUserCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }

    fun getChatRoomReference(chatroomId: String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId)
    }

    fun getChatRoomId(userId1: String, userId2: String): String {
        if (userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2
        }else{
            return userId2+"_"+userId1
        }
    }
}