package com.vinh.data.itemviewmodel

sealed class ModelListItem {

    object Header : ModelListItem()

    data class ContentItem(val content: String) : ModelListItem(){
        companion object
    }
}