package com.lyhoangvinh.simple.ui.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.utils.genericCastOrNull
import com.lyhoangvinh.simple.utils.inflate

abstract class BasePagedAdapter<T: Any, B : ViewDataBinding>(diffUtil: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseViewHolder<B>>(diffUtil) {

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View):  BaseViewHolder<B>

    protected abstract fun onBindViewHolder(binding: B, dto: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        return genericCastOrNull(createViewHolder(parent.inflate(itemLayoutResource())))
    }

    override fun onBindViewHolder(vh:  BaseViewHolder<B>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            this.onBindViewHolder(vh.binding, item, position)
            vh.binding.executePendingBindings()
        }
    }
}