package com.vinh.domain.itemviewmodel

import com.vinh.domain.model.entities.avgle.Collection
import com.vinh.domain.model.ItemViewModel

data class CollectionBottomItem(var collections: List<Collection>?) : ItemViewModel {
    override val idViewModel: String?
        get() = collections?.size.toString()
}
