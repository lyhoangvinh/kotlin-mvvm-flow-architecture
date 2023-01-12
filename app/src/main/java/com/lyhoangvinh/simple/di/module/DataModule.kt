package com.lyhoangvinh.simple.di.module

import android.content.Context
import androidx.room.Room
import com.vinh.data.DatabaseManager
import com.vinh.data.SharedPrefs
import com.vinh.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): DatabaseManager {
        return Room.databaseBuilder(context, DatabaseManager::class.java, "my-database")
            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    internal fun providesSharedPrefs(@ApplicationContext context: Context): SharedPrefs = SharedPrefs.getInstance(context)

    @Provides
    @Singleton
    fun provideIssuesDao(databaseManager: DatabaseManager): IssuesDao = databaseManager.issuesDao()

    @Provides
    @Singleton
    fun provideCategoriesDao(databaseManager: DatabaseManager): CategoriesDao = databaseManager.categoriesDao()

    @Provides
    @Singleton
    fun provideCollectionDao(databaseManager: DatabaseManager): CollectionDao = databaseManager.collectionDao()

    @Provides
    @Singleton
    fun provideVideosDao(databaseManager: DatabaseManager): VideosDao = databaseManager.videosDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(databaseManager: DatabaseManager): FavoriteDao = databaseManager.favoriteDao()

    @Provides
    @Singleton
    fun provideSearchHistoryDao(databaseManager: DatabaseManager): SearchHistoryDao = databaseManager.searchHistoryDao()

    @Provides
    @Singleton
    fun provideVideoRemoteKeyDao(databaseManager: DatabaseManager): VideoRemoteKeyDao = databaseManager.videoRemoteKeyDao()
}