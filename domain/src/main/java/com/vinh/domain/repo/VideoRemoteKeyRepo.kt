package com.vinh.domain.repo

import com.vinh.domain.model.entities.avgle.VideoRemoteKey

interface VideoRemoteKeyRepo {
    suspend fun remoteKeyByExecutor(executor: String): VideoRemoteKey
    suspend fun deleteByExecutor(executor: String)
    suspend fun insert(videoRemoteKey: VideoRemoteKey)
}