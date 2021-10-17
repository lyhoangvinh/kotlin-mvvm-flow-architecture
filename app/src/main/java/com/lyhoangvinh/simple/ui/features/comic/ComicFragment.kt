package com.lyhoangvinh.simple.ui.features.comic


import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentComicBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicFragment : BaseViewModelRecyclerViewPagingFragment<FragmentComicBinding, ComicViewModel, ComicAdapter>() {
    override val viewModel: ComicViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_comic
}

