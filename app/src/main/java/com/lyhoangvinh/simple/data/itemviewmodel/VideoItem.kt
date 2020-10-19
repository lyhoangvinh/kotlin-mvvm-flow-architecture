package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class VideoItem(var videos: List<Video>?) :
    ItemViewModel {
    override val idViewModel: String?
        get() = videos?.size.toString()
}