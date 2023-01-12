package com.vinh.domain.model.entities

data class OptionEntity(
    val index: Int,
    var isCheck: Boolean? = false,
    val name: String
)