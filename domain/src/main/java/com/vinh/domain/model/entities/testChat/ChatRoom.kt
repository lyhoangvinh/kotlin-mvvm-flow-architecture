package com.vinh.domain.model.entities.testChat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vinh.domain.model.entities.testChat.stringcover.UserProfile

@Entity
data class ChatRoom(
    @PrimaryKey
    var id: String = "",
    var createdBy: String = "",
    var createdAt: Long = 0,
    var about: String = "",
    var image: String = "",
    var members: ArrayList<ChatUser>? = null, //only for storing in localdb
    var profiles: ArrayList<UserProfile>? = null,
    var unRead: Int = 0
)