package com.vinh.domain.usecases

import androidx.paging.map
import com.vinh.domain.interactor.*
import com.vinh.domain.model.ItemViewModel
import com.vinh.domain.repo.VideoRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetVideoPagingSourceByExecutor @Inject constructor(
    private val getAllVideos: GetAllVideos,
    private val videoRepo: VideoRepo,
    private val getVideoRemoteKeyByExecutor: GetVideoRemoteKeyByExecutor,
    private val deleteVideoRemoteByExecutor: DeleteVideoRemoteByExecutor,
    private val deleteVideoByExecutor: DeleteVideoByExecutor,
    private val addVideoRemoteKey: AddVideoRemoteKey,
    private val addVideoList: AddVideoList
) {
    operator fun invoke(executor: String) = videoRepo.videoOfExecutor(
        executor = executor,
        getAllVideos = getAllVideos,
        getVideoRemoteKeyByExecutor = getVideoRemoteKeyByExecutor,
        deleteVideoRemoteByExecutor = deleteVideoRemoteByExecutor,
        deleteVideoByExecutor = deleteVideoByExecutor,
        addVideoRemoteKey = addVideoRemoteKey,
        addVideoList = addVideoList
    ) 
}