package com.vinh.data.itemviewmodel

import com.vinh.domain.model.entities.avgle.Collection
import com.vinh.domain.model.ItemViewModel

data class CollectionBannerItem(var collections: List<Collection>?): ItemViewModel {
    override val idViewModel: String?
        get() = collections?.size.toString()
}