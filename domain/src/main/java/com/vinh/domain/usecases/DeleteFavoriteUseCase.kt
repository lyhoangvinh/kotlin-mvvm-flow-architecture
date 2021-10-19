package com.vinh.domain.usecases

import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val favoriteRepo: FavoriteRepo) {
    suspend operator fun invoke(ids: List<String>) = favoriteRepo.deleteFavorites(ids)
}