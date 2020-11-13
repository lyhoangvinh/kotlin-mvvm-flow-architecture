package com.vinh.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.vinh.data.comon.zip
import com.vinh.data.comon.zipFlow
import com.vinh.data.itemviewmodel.*
import com.vinh.data.services.AvgleService
import com.vinh.domain.itemviewmodel.*
import com.vinh.domain.model.ItemViewModel
import com.vinh.domain.model.Resource
import com.vinh.domain.model.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AvgleSource @Inject constructor(private val avgleService: AvgleService) : BaseDataSource() {

    suspend fun fetchDataHome(): Flow<Resource<List<ItemViewModel>>> = flow {
        emit(Resource.loading())
        val categories = getResource { avgleService.getCategories() }
        val collection1 =  getResource { avgleService.getCollections((1..10).random(), (5..10).random()) }
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

    suspend fun homeLiveData(): LiveData<Resource<List<ItemViewModel>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val categories = getResource { avgleService.getCategories() }
        val collection1 =  getResource { avgleService.getCollections((1..10).random(), (5..10).random()) }
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
        emit(Resource.success(items.toList()))
    }

    private suspend fun zipHomeDataAsync(): Deferred<List<ItemViewModel>> = coroutineScope {
        zip(async {getResource { avgleService.getCategories() } },
            async { getResource { avgleService.getCollections((1..10).random(), (5..10).random()) } },
            async { getResource { avgleService.getCollections(1, 10) } },
            async { getResource { avgleService.getAllVideos((0..10).random()) } })
        { categories ,collection1,collection2, video ->
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
            items
        }
    }

    suspend fun getHomeData6(): Flow<Resource<List<ItemViewModel>>> = flow {
        emit(Resource.loading())
        val job = zipHomeDataAsync()
        emit(Resource.success(job.await()))
        job.cancelAndJoin()
    }

    suspend fun fetchDataHome7(): Flow<Resource<List<ItemViewModel>>> = flow {
        emit(Resource.loading())
        val source1 = getResource { avgleService.getCategories() }
        val source2 = getResource { avgleService.getCollections((1..10).random(), (5..10).random()) }
        val source3 =  getResource { avgleService.getCollections(1, 10)}
        val source4 = getResource { avgleService.getAllVideos((0..10).random()) }
        Log.d("LOG_THREAD", "NGoai " + Thread.currentThread().name)
       emitAll(zipFlow(source1 = {source1}, source2 =  {source2}, source3 =  {source3}, source4 =  {source4}) {
                categories ,collection1, collection2, video ->
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
           Log.d("LOG_THREAD",  Thread.currentThread().name)
            Resource.success(items.toList())
        }.flowOn(Dispatchers.Default))
    }
}