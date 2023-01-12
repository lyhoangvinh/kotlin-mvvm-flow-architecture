package com.vinh.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vinh.data.DatabaseManager
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

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Video>
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    // Query DB for SubredditRemoteKey for the subreddit.
                    // SubredditRemoteKey is a wrapper object we use to keep track of page keys we
                    // receive from the Reddit API to fetch the next or previous page.
                    val remoteKey = databaseManager.withTransaction {
                        getVideoRemoteKeyByExecutor(executor)
                    }

                    // We must explicitly check if the page key is null when appending, since the
                    // Reddit API informs the end of the list by returning null for page key, but
                    // passing a null key to Reddit API will fetch the initial page.
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