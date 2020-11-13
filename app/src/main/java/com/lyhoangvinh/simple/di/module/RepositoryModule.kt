package com.lyhoangvinh.simple.di.module

import com.vinh.data.repo.AvgRepoImpl
import com.vinh.data.repo.ComicRepoImpl
import com.vinh.domain.repo.AvgRepo
import com.vinh.domain.repo.ComicRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    abstract fun comicRepo(repoImpl: ComicRepoImpl): ComicRepo

    @Binds
    abstract fun avgRepo(repoImpl: AvgRepoImpl): AvgRepo
}