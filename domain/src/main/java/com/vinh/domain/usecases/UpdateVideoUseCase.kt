package com.vinh.domain.usecases

import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class UpdateVideoUseCase @Inject constructor(private val videoRepo: VideoRepo) {
    operator fun invoke(video: Video) = videoRepo.update(video)
}