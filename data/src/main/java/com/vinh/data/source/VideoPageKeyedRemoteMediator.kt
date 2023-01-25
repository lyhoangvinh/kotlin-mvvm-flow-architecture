package com.vinh.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vinh.data.manager.DatabaseManager
import com.vinh.domain.interactor.*
import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.model.entities.avgle.VideoRemoteKey
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class VideoPageKeyedRemoteMediator(
    private val executor: String,
    private val databaseManager: DatabaseManager,
    private val getAllVideos: GetAllVideos,
    private val getVideoRemoteKeyByExecutor: GetVideoRemoteKeyByExecutor,
    private val deleteVideoRemoteByExecutor: DeleteVideoRemoteByExecutor,
    private val deleteVideoByExecutor: DeleteVideoByExecutor,
    private val addVideoRemoteKey: AddVideoRemoteKey,
    private val addVideoList: AddVideoList
) : RemoteMediator<Int, Video>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Video>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val remoteKey = databaseManager.withTransaction {
                        getVideoRemoteKeyByExecutor(executor)
                    }
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey ?: 0
                }
            }
            val data =
                getAllVideos(loadKey).body()?.response?.listData()?.mapIndexed { index, video ->
                    video.copy(
                        indexInResponse = index, executor = executor
                    )
                }.orEmpty()
            databaseManager.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deleteVideoRemoteByExecutor(executor)
                    deleteVideoByExecutor(executor)
                }
                addVideoRemoteKey(VideoRemoteKey(executor, loadKey + 1))
                addVideoList(data)
            }
            return MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}