package com.example.skillexchange.models

import com.google.firebase.Timestamp

class ChatRoomModel {

    var chatRoomId: String = ""
    var lastMessageSenderId: String = ""
    var userIds: List<String> = emptyList()
    private var _lastMessageTime: Timestamp = Timestamp.now()

    // Getter для lastMessageTime
    var lastMessageTime: Timestamp
        get() = _lastMessageTime
        set(value) {
            _lastMessageTime = value
        }

    constructor()

    constructor(
        chatRoomId: String,
        lastMessageSenderId: String,
        userIds: List<String>,
        lastMessageTime: Timestamp
    ) {
        this.chatRoomId = chatRoomId
        this.lastMessageSenderId = lastMessageSenderId
        this.userIds = userIds
        this.lastMessageTime = lastMessageTime
    }
}