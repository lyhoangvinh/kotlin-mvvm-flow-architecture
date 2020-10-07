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
import com.lyhoangvinh.simple.utils.PreCachingLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicFragment : BaseViewModelFragment<FragmentComicBinding, ComicViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {
    @Inject lateinit var adapter: ComicAdapter
    override val viewModel: ComicViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_comic
    override fun initialize(view: View, ctx: Context?) {
        viewModel.initAdapter(adapter)
        binding.refreshLayout.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(R.color.material_amber_700, R.color.material_blue_700, R.color.material_purple_700, R.color.material_lime_700)
        }
        with(binding) {
//            setVariable(BR.vm, viewModel)
            recyclerView.let {
                it.layoutManager = PreCachingLayoutManager(ctx)
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = adapter
                it.setHasFixedSize(true)
            }

            adapter.addLoadStateListener { loadState ->
                setRefreshLayout(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading, this)
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    Log.d("BaseViewModelRcv","errorState: ${it.error.message}")
                }
            }
        }
    }

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            viewModel.refresh()
        }
    }
    private var isRefreshing: Boolean = false

    private fun setRefreshLayout(loading: Boolean, binding: FragmentComicBinding) {
        if (!loading) {
            binding.refreshLayout.isRefreshing = false
            isRefreshing = false
        } else {
            binding.refreshLayout.isRefreshing = true
        }
    }
}

