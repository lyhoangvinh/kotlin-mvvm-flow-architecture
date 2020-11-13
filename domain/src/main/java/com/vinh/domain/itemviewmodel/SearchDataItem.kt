package com.vinh.domain.itemviewmodel

import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.model.ItemViewModel

data class SearchDataItem(val video: Video, override val idViewModel: String?) : ItemViewModel