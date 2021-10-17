package com.vinh.domain.usecases

import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class AddOrDeleteFavoriteUseCase @Inject constructor(private val favoriteRepo: FavoriteRepo) {
    suspend operator fun invoke(video: Video) {
        if (video.favorite == true) {
            favoriteRepo.deleteFavorite(video.vid)
        } else {
            favoriteRepo.addFavorite(video.vid)
        }
    }
}