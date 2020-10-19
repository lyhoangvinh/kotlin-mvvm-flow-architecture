package com.lyhoangvinh.simple.ui.base.adapter

import com.lyhoangvinh.simple.data.entities.avgle.Category

sealed class ItemData
object SearchItemData2: ItemData()
object DividerItem2: ItemData()
data class CategoryItem2(
    var categories: List<Category>?
): ItemData()