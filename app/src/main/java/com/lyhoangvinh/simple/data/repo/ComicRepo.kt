package com.lyhoangvinh.simple.data.repo

import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import kotlinx.coroutines.flow.Flow

interface ComicRepo {
    fun getData() : Flow<PagingData<Issues>>
}