package com.vinh.domain.itemviewmodel

import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.model.ItemViewModel

data class VideoItem(var videos: List<Video>?) :
    ItemViewModel {
    override val idViewModel: String
        get() = videos?.size.toString()
}
