package com.lyhoangvinh.simple.ui.base.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.lyhoangvinh.simple.utils.PreCachingLayoutManager
import com.lyhoangvinh.simple.utils.hideKeyboard
import com.vinh.domain.model.State
import kotlinx.android.synthetic.main.view_recyclerview.*
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewPagingFragment<B : ViewDataBinding,
        VM : BaseListViewModel<A>,
        A : PagingDataAdapter<*, *>> : BaseViewModelFragment<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    private var isRefreshing: Boolean = false

    private var layoutManager: RecyclerView.LayoutManager? = null

    private var errorState : LoadState.Error?= null

    override fun getLayoutResource() = R.layout.view_recyclerview

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize(view: View, ctx: Context?) {
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        recyclerView.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = adapter
            it.setOnTouchListener { _, _ ->
                hideKeyboard()
                false
            }
            it.setHasFixedSize(hasFixedSize())
        }
        refreshLayout?.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(R.color.material_amber_700, R.color.material_blue_700, R.color.material_purple_700, R.color.material_lime_700)
        }
        adapter.addLoadStateListener { loadState ->
            setRefreshLayout(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            errorState?.let {
                if (hasHandleErrorState() && this.errorState != errorState) {
                    this.errorState = errorState
                    viewModel.stateLiveData.setValue(State.error(it))
                }
                Log.d("BaseViewModelRcv","errorState: ${it.error.message}")
            }
        }
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = PreCachingLayoutManager(activity)

    open fun hasHandleErrorState() : Boolean = true

    open fun hasFixedSize() = false

    private fun setRefreshLayout(loading: Boolean) {
        if (!loading) {
            refreshLayout?.isRefreshing = false
            isRefreshing = false
        } else {
            refreshLayout?.isRefreshing = true
        }
    }

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            viewModel.refresh()
        }
    }

    fun getRecyclerView() : RecyclerView = this.recyclerView
}