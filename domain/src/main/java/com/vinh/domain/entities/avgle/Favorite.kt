package com.vinh.domain.entities.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    var id: String
)