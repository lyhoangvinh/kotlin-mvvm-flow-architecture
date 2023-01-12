package com.vinh.domain.interactor

import com.vinh.domain.model.entities.avgle.VideoRemoteKey
import com.vinh.domain.repo.VideoRemoteKeyRepo
import javax.inject.Inject

class DeleteVideoRemoteByExecutor @Inject constructor(private val remoteKeyRepo: VideoRemoteKeyRepo) {
    suspend operator fun invoke(executor: String) = remoteKeyRepo.deleteByExecutor(executor)
}