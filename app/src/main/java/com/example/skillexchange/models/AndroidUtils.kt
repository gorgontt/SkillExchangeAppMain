package com.example.skillexchange.models

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

open class AndroidUtils {


    fun passUserModelAsIntent(intent: Intent, model: UserRv) {
        intent.putExtra("username", model.name)
        intent.putExtra("userId", model.userId)
        intent.putExtra("userEmail", model.userEmail)
    }




}