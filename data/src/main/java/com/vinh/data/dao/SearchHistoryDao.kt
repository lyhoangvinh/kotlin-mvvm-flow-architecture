package com.vinh.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.vinh.domain.entities.avgle.SearchHistory


@Dao
interface SearchHistoryDao : BaseDao<SearchHistory> {
    @Query("SELECT * FROM SearchHistory")
    fun liveDataFactory(): DataSource.Factory<Int, SearchHistory>


}