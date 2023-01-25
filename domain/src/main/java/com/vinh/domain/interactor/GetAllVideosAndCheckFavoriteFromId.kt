package com.vinh.domain.interactor

import javax.inject.Inject

class GetAllVideosAndCheckFavoriteFromId @Inject constructor(
    private val getVideos: GetAllVideos,
    private val getFavoriteVideoIds: GetAllFavoriteVideoIds
) {
    suspend operator fun invoke(page: Int) = run {
        val favoriteVideos = getFavoriteVideoIds()
        getVideos(page).run {
            val response = body()?.response
            val data = response?.listData()?.onEach {
                it.favorite = favoriteVideos.any { id -> id == it.vid }
            }.orEmpty()
            data
        }
    }
}