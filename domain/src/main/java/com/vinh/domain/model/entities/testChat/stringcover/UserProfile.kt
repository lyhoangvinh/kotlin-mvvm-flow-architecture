package com.vinh.domain.model.entities.testChat.stringcover

data class UserProfile(var userId: String?=null,var createdAt: Long?=null,
                       var updatedAt: Long?=null,
                       var image: String="",
                       var userName: String="",
                       var about: String="",
                       var token :String="")