package com.example.skillexchange.models

import com.google.firebase.auth.FirebaseAuth


class FirebaseUtils {

    companion object{

        private val auth = FirebaseAuth.getInstance()
        private var userid: String = ""


        fun getUiLoggedIn(): String{

            if (auth.currentUser != null){
                userid = auth.currentUser!!.uid
            }
            return userid
        }
    }
}