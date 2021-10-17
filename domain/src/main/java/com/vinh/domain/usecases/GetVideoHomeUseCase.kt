package com.vinh.domain.usecases

import com.vinh.domain.interactor.GetAllFavoriteVideos
import com.vinh.domain.interactor.GetAllVideos
import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class GetVideoHomeUseCase @Inject constructor(
    private val videoRepo: VideoRepo,
    private val videos: GetAllVideos,
    private val favoriteVideos: GetAllFavoriteVideos
) {
    operator fun invoke() = videoRepo.getPaging(videos, favoriteVideos)
}