package com.vinh.domain.interactor

import com.vinh.domain.model.entities.avgle.VideoRemoteKey
import com.vinh.domain.repo.VideoRemoteKeyRepo
import javax.inject.Inject

class AddVideoRemoteKey @Inject constructor(private val videoRemoteKeyRepo: VideoRemoteKeyRepo) {
    suspend operator fun invoke(videoRemoteKey :VideoRemoteKey) = videoRemoteKeyRepo.insert(videoRemoteKey)
}