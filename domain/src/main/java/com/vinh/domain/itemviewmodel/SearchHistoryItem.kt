package com.vinh.domain.itemviewmodel

import com.vinh.data.entities.avgle.SearchHistory
import com.vinh.domain.model.ItemViewModel


data class SearchHistoryItem(var data: SearchHistory, override val idViewModel: String?) :
    ItemViewModel