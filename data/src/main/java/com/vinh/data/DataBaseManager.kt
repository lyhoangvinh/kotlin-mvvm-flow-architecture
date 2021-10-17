package com.vinh.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinh.data.dao.*
import com.vinh.data.entities.comic.Comics
import com.vinh.data.entities.comic.Issues
import com.vinh.data.typecoverter.ImageTypeConverter
import com.vinh.data.typecoverter.VolumeTypeConverter
import com.vinh.domain.entities.avgle.*
import com.vinh.domain.entities.avgle.Collection

@Database(
    entities = [Comics::class, Issues::class, Category::class, Collection::class, Video::class, SearchHistory::class, Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ImageTypeConverter::class, VolumeTypeConverter::class)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun issuesDao(): IssuesDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun collectionDao(): CollectionDao

    abstract fun videosDao(): VideosDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    abstract fun favoriteDao(): FavoriteDao
}
