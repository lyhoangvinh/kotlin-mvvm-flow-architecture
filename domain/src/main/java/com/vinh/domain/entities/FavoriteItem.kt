package com.vinh.domain.entities

import com.vinh.domain.entities.avgle.Favorite

data class FavoriteItem(val favorite: Favorite, var isChecked: Boolean?= false)