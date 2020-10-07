package com.lyhoangvinh.simple.ui.features.comic2


import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentComic2Binding
import com.lyhoangvinh.simple.databinding.FragmentComicBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment
import com.lyhoangvinh.simple.ui.features.comic.ComicAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicFragment2 : BaseViewModelFragment<FragmentComic2Binding, ComicViewModel2>() {
    override val viewModel: ComicViewModel2 by viewModels()
    override fun getLayoutResource() = R.layout.fragment_comic2
    override fun initialize(view: View, ctx: Context?) {
        binding.setVariable(BR.vm, viewModel)
    }
}

