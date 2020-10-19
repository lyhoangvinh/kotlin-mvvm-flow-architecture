package com.lyhoangvinh.simple.data.itemviewmodel

import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CategoryItem(
    var categories: List<Category>?
) : ItemViewModel {
    override val idViewModel: String?
        get() = categories?.size.toString()
}