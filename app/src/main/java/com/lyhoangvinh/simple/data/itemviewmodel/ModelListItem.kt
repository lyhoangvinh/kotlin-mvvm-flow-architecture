package com.lyhoangvinh.simple.data.itemviewmodel

sealed class ModelListItem {

    object Header : ModelListItem()

    data class ContentItem(val content: String) : ModelListItem(){
        companion object
    }
}