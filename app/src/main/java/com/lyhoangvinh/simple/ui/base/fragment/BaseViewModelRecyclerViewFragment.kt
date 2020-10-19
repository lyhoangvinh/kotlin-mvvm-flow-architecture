package com.lyhoangvinh.simple.ui.base.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.lyhoangvinh.simple.utils.PreCachingLayoutManager
import kotlinx.android.synthetic.main.view_recyclerview.*
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewFragment<B : ViewDataBinding,
        VM : BaseListViewModel<A>,
        A : RecyclerView.Adapter<*>> : BaseViewModelFragment<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    private var isRefreshing: Boolean = false

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun getLayoutResource() = R.layout.view_recyclerview

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize(view: View, ctx: Context?) {
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(hasStableIds())
        }
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        recyclerView.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = adapter
            it.setHasFixedSize(hasFixedSize())
        }
        refreshLayout?.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(R.color.material_amber_700, R.color.material_blue_700, R.color.material_purple_700, R.color.material_lime_700)
        }
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = PreCachingLayoutManager(activity)

    open fun hasFixedSize() = false

    open fun hasStableIds() = false

    override fun setLoading(loading: Boolean) {
        if (refreshLayout == null){
            super.setLoading(loading)
        }else{
            if (!loading) {
                refreshLayout?.isRefreshing = false
                isRefreshing = false
            } else {
                refreshLayout?.isRefreshing = true
            }
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