package com.lyhoangvinh.simple.di.module


import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.repo.impl.ComicRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun comicRepo(repoImpl: ComicRepoImpl): ComicRepo
}