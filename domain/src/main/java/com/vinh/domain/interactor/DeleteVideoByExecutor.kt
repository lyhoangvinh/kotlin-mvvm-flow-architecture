package com.vinh.domain.interactor

import com.vinh.domain.repo.VideoRepo
import javax.inject.Inject

class DeleteVideoByExecutor @Inject constructor(private val videoRepo: VideoRepo) {
    suspend operator fun invoke(executor: String) = videoRepo.deleteByExecutor(executor)
}