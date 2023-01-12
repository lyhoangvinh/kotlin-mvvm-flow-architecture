package com.vinh.domain.interactor

import com.vinh.domain.repo.VideoRemoteKeyRepo
import javax.inject.Inject

class GetVideoRemoteKeyByExecutor @Inject constructor(private val remoteKeyRepo: VideoRemoteKeyRepo) {
    suspend operator fun invoke(executor: String) = remoteKeyRepo.remoteKeyByExecutor(executor)
}