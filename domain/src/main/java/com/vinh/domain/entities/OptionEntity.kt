package com.vinh.domain.entities

data class OptionEntity(
    val index: Int,
    var isCheck: Boolean? = false,
    val name: String
)