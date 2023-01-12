package com.vinh.domain.model.entities

import com.vinh.domain.model.entities.avgle.Favorite

data class FavoriteItem(val favorite: Favorite, var isChecked: Boolean?= false)