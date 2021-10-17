package com.lyhoangvinh.simple.di.module

import com.vinh.data.repo.AvgRepoImpl
import com.vinh.data.repo.ComicRepoImpl
import com.vinh.data.repo.FavoriteRepoImpl
import com.vinh.data.repo.VideoRepoImpl
import com.vinh.domain.repo.AvgRepo
import com.vinh.domain.repo.ComicRepo
import com.vinh.domain.repo.FavoriteRepo
import com.vinh.domain.repo.VideoRepo
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
}