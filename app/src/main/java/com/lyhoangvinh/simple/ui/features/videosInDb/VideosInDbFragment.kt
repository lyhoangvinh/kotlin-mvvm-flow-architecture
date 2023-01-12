package com.lyhoangvinh.simple.ui.features.videosInDb

import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import com.lyhoangvinh.simple.ui.features.videos.videohome.VideoHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview


@FlowPreview
@AndroidEntryPoint
class VideosInDbFragment :
    BaseViewModelRecyclerViewPagingFragment<ViewRecyclerviewBinding, VideosInDbViewModel, VideoHomeAdapter>() {
    override val viewModel: VideosInDbViewModel by viewModels()
    override fun hasHandleErrorState() = true
}