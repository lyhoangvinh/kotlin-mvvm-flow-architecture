package com.vinh.data.repo

import com.vinh.data.dao.VideoRemoteKeyDao
import com.vinh.domain.model.entities.avgle.VideoRemoteKey
import com.vinh.domain.repo.VideoRemoteKeyRepo
import javax.inject.Inject

class VideoRemoteKeyRepoImpl @Inject constructor(private val remoteKeyDao: VideoRemoteKeyDao) :
    VideoRemoteKeyRepo {

    override suspend fun remoteKeyByExecutor(executor: String): VideoRemoteKey = remoteKeyDao.remoteKeyByExecutor(executor)

    override suspend fun deleteByExecutor(executor: String) = remoteKeyDao.deleteByExecutor(executor)

    override suspend fun insert(videoRemoteKey: VideoRemoteKey) = remoteKeyDao.insert(videoRemoteKey)
}