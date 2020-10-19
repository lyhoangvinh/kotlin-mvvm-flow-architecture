package com.lyhoangvinh.simple.ui.base.adapter

import com.lyhoangvinh.simple.data.entities.avgle.Category

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