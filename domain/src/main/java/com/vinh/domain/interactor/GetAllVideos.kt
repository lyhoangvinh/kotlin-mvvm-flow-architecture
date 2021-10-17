package com.vinh.domain.interactor

import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class GetAllVideos @Inject constructor(private val videoRepo: VideoRepo) {
    suspend operator fun invoke(page: Int) = videoRepo.getAll(page)
}