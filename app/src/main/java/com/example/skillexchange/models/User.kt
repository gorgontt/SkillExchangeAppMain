package com.example.skillexchange.models

import com.google.firebase.Timestamp

class User {

    var image: String = ""
    var userName: String = ""
    var userAge: String = ""
    var timestamp: Timestamp = Timestamp.now()

    constructor()
    constructor(
        image: String,
        userName: String,
        userAge: String,
        timestamp: Timestamp
    ) {
        this.image = image
        this.userName = userName
        this.userAge = userAge
        this.timestamp = timestamp
    }

}