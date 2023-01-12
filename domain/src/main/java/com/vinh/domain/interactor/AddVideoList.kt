package com.vinh.domain.interactor

import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class AddVideoList @Inject constructor(private val videoRepo: VideoRepo) {
    suspend operator fun invoke(videos: List<Video>) = videoRepo.insertList(videos)
}