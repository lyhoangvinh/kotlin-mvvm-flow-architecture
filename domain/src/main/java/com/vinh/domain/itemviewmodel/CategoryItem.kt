package com.vinh.domain.itemviewmodel

import com.vinh.domain.model.entities.avgle.Category
import com.vinh.domain.model.ItemViewModel

data class CategoryItem(
    var categories: List<Category>?
) : ItemViewModel {
    override val idViewModel: String?
        get() = categories?.size.toString()
}