package com.lyhoangvinh.simple.ui.features.comic


import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentComicBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import com.lyhoangvinh.simple.utils.PreCachingLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicFragment : BaseViewModelRecyclerViewPagingFragment<FragmentComicBinding, ComicViewModel, ComicAdapter>(),
    SwipeRefreshLayout.OnRefreshListener {
    override val viewModel: ComicViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_comic
}

