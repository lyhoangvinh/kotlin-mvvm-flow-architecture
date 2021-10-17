package com.vinh.domain.interactor

import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class GetAllFavoriteVideos @Inject constructor(private val repo: FavoriteRepo) {
    suspend operator fun invoke() = repo.getFavorite()
}