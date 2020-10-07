package com.lyhoangvinh.simple.ui.base.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView


abstract class BaseListViewModel<A : RecyclerView.Adapter<*>> : BaseViewModel() {

    @Nullable
    lateinit var adapter: A

    @CallSuper
    open fun initAdapter(@NonNull adapter: A) {
        this.adapter = adapter
    }

    /**
     * refreshUi All
     */
    @CallSuper
    fun refresh() {
        fetchData()
    }

    protected abstract fun fetchData()

}