package com.vinh.domain.model.entities.testChat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vinh.domain.model.entities.testChat.stringcover.*

@Entity
data class Message(
    @PrimaryKey
    val createdAt: Long,
    var seenTime: Long = 0L,
    val from: String, val to: String,
    val senderName: String,
    val senderImage: String,
    var type: String = "text",//0=text,1=audio,2=image,3=video,4=file
    var status: Int = 0,//0=sending,1=sent,2=delivered,3=seen,4=failed
    var textMessage: TextMessage? = null,
    var imageMessage: ImageMessage? = null,
    var audioMessage: AudioMessage? = null,
    var videoMessage: VideoMessage? = null,
    var fileMessage: FileMessage? = null,
    var chatUsers: ArrayList<String>? = null,
    var chatUserId: String? = null
)






