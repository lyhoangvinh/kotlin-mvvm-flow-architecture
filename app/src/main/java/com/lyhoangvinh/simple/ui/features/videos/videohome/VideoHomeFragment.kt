package com.lyhoangvinh.simple.ui.features.videos.videohome

import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class VideoHomeFragment : BaseViewModelRecyclerViewPagingFragment<ViewRecyclerviewBinding, VideoHomeViewModel, VideoHomeAdapter>() {
    override val viewModel: VideoHomeViewModel by viewModels()
}