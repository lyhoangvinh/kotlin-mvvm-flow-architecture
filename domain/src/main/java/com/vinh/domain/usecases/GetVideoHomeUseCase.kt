package com.vinh.domain.usecases

import com.vinh.domain.interactor.GetAllFavoriteVideoIds
import com.vinh.domain.interactor.GetAllVideos
import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class GetVideoHomeUseCase @Inject constructor(
    private val videoRepo: VideoRepo,
    private val videos: GetAllVideos,
    private val favoriteVideoIds: GetAllFavoriteVideoIds
) {
    operator fun invoke() = videoRepo.getPaging(videos, favoriteVideoIds)
}