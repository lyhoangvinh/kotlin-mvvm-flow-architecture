package com.vinh.domain.interactor

import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class GetAllFavoriteVideoIds @Inject constructor(private val repo: FavoriteRepo) {
    suspend operator fun invoke() = repo.getFavorite().map { it.id }
}