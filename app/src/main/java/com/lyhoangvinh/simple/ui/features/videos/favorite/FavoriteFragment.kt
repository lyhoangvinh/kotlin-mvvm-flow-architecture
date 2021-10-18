package com.lyhoangvinh.simple.ui.features.videos.favorite

import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class FavoriteFragment :
    BaseViewModelRecyclerViewPagingFragment<ViewRecyclerviewBinding, FavoriteViewModel, FavoriteAdapter>() {
    override val viewModel: FavoriteViewModel by viewModels()
}