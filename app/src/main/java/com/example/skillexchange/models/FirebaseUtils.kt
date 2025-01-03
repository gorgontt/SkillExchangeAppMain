package com.example.skillexchange.models

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date


class FirebaseUtils {
    companion object {
        private val auth = FirebaseAuth.getInstance()
        private var userid: String = ""

        fun getUiLoggedIn(): String {
            if (auth.currentUser != null) {
                userid = auth.currentUser!!.uid
            }
            return userid
        }

        fun getTime(): String {
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            return formatter.format(date)
        }
    }
}