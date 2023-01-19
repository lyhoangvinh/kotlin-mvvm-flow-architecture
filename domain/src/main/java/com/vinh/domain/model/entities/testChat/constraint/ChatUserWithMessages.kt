package com.vinh.domain.model.entities.testChat.constraint

import androidx.room.Embedded
import androidx.room.Relation
import com.vinh.domain.model.entities.testChat.ChatUser
import com.vinh.domain.model.entities.testChat.Message

class ChatUserWithMessages(
    @Embedded
    val user: ChatUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatUserId"
    )
    val messages: List<Message>
)