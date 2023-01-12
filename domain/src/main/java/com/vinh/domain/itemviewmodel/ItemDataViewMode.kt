package com.vinh.domain.itemviewmodel

import com.vinh.domain.model.entities.avgle.Category
import com.vinh.domain.model.ItemViewModel

sealed class ItemData : ItemViewModel
object SearchItemData2: ItemData() {
    override val idViewModel: String?
        get() = null
}

object DividerItem2: ItemData() {
    override val idViewModel: String?
        get() = null
}

data class CategoryItem2(
    var categories: List<Category>?
): ItemData() {
    override val idViewModel: String?
        get() = categories?.size.toString()
}