package com.vinh.domain.model.entities.testChat


import androidx.room.PrimaryKey
import com.vinh.domain.model.entities.testChat.stringcover.UserProfile

data class ChatUser(
    @PrimaryKey
    var id: String, var localName: String,
    var user: UserProfile,
    var unRead: Int = 0
)