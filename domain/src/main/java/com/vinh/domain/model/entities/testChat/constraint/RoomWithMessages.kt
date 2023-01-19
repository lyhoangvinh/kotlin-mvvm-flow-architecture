package com.vinh.domain.model.entities.testChat.constraint

import androidx.room.Embedded
import androidx.room.Relation
import com.vinh.domain.model.entities.testChat.ChatRoom
import com.vinh.domain.model.entities.testChat.RoomMessage

class RoomWithMessages(
    @Embedded
    val chatRoom: ChatRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val messages: List<RoomMessage>
)