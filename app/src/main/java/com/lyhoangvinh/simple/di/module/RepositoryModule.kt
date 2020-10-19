package com.lyhoangvinh.simple.di.module


import com.lyhoangvinh.simple.data.repo.AvgRepo
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.repo.impl.AvgRepoImpl
import com.lyhoangvinh.simple.data.repo.impl.ComicRepoImpl
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