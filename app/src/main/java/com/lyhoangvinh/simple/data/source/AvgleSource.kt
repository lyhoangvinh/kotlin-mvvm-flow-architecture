package com.lyhoangvinh.simple.data.source

import com.google.android.play.core.splitinstall.f
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.itemviewmodel.*
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.ui.base.adapter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AvgleSource @Inject constructor(private val avgleService: AvgleService) : BaseDataSource() {

    suspend fun fetchDataHome(): Flow<Resource<List<ItemViewModel>>> = flow {
        emit(Resource.loading())
        val categories = getResource { avgleService.getCategories() }
        val collection1 =   getResource { avgleService.getCollections((1..10).random(), (5..10).random()) }
        val collection2 =  getResource { avgleService.getCollections(1, 10) }
        val video =  getResource { avgleService.getAllVideos((0..10).random()) }
        val items = mutableListOf<ItemViewModel>()
        items.add(DividerItem("DividerItem-{0}"))
        items.add(SearchItem(null))
        items.add(DividerItem("DividerItem-{0}"))
        if (categories.status == Status.SUCCESS) {
            items.add(CategoryItem(categories.data?.response?.categories.orEmpty()))
            items.add(DividerItem("DividerItem-{1}"))
        }
        if (collection1.status == Status.SUCCESS) {
            items.add(CollectionBannerItem(collection1.data?.response?.collections.orEmpty()))
            items.add(DividerItem("DividerItem-{2}"))
        }
        if (collection2.status == Status.SUCCESS) {
            items.add(TitleSeeAllItem("Collections"))
            items.add(DividerItem("DividerItem-{3}"))
            items.add(CollectionBottomItem(collection2.data?.response?.collections.orEmpty()))
            items.add(DividerItem("DividerItem-{4}"))
        }

        if (video.status == Status.SUCCESS) {
            items.add(TitleSeeAllItem("Videos"))
            items.add(DividerItem("DividerItem-{5}"))
            items.add(VideoItem(video.data?.response?.videos.orEmpty()))
            items.add(DividerItem("DividerItem-{6}"))
        }
        emit(Resource.success(items))
    }.flowOn(Dispatchers.IO)

    suspend fun fetchDataHome2(): Flow<Resource<List<ItemData>>> = flow {
        emit(Resource.loading())
        val categories = getResource { avgleService.getCategories() }
//        val collection1 =   getResource { avgleService.getCollections((1..10).random(), (5..10).random()) }
//        val collection2 =  getResource { avgleService.getCollections(1, 10) }
//        val video =  getResource { avgleService.getAllVideos((0..10).random()) }
        val items = mutableListOf<ItemData>()
        items.add(DividerItem2)
        items.add(SearchItemData2)
        items.add(DividerItem2)
        if (categories.status == Status.SUCCESS) {
            items.add(CategoryItem2(categories.data?.response?.categories.orEmpty()))
            items.add(DividerItem2)
        }
        emit(Resource.success(items))
    }.flowOn(Dispatchers.IO)
}