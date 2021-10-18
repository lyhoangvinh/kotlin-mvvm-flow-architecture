package com.vinh.domain.usecases

import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class GetFavoriteCountUseCase @Inject constructor(private val favoriteRepo: FavoriteRepo) {
    operator fun invoke() = favoriteRepo.getCount()
}