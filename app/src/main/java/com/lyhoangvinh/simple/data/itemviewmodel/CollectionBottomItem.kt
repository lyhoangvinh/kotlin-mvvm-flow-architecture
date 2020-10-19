package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CollectionBottomItem(var collections: List<Collection>?) : ItemViewModel {
    override val idViewModel: String?
        get() = collections?.size.toString()
}
