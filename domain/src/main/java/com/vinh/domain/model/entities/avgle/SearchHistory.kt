package com.vinh.domain.model.entities.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var keyword: String? = null,

    var url: String? = null,

    var timestamp: String? = null
)