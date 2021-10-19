package com.lyhoangvinh.simple.ui.features.videos.favorite

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentFavoriteBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class FavoriteFragment :
    BaseViewModelRecyclerViewFragment<FragmentFavoriteBinding, FavoriteViewModel, FavoriteAdapter>() {
    override fun getLayoutResource() = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by viewModels()
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        binding.setVariable(BR.vm, viewModel)
    }
}