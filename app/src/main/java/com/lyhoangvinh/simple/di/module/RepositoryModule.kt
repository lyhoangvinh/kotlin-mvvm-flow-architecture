package com.lyhoangvinh.simple.di.module

import com.vinh.data.repo.*
import com.vinh.domain.repo.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    abstract fun comicRepo(repoImpl: ComicRepoImpl): ComicRepo

    @Binds
    abstract fun avgRepo(repoImpl: AvgRepoImpl): AvgRepo

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    abstract fun videoRepo(repoImpl: VideoRepoImpl): VideoRepo

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    abstract fun favoriteRepo(repoImpl: FavoriteRepoImpl): FavoriteRepo


    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    abstract fun videoRemoteKeyRepo(repoImpl: VideoRemoteKeyRepoImpl): VideoRemoteKeyRepo
}