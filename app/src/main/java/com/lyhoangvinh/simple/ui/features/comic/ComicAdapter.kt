package com.lyhoangvinh.simple.ui.features.comic

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.vinh.domain.model.entities.comic.Issues
import com.lyhoangvinh.simple.databinding.ItemComicsBinding
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import javax.inject.Inject

class ComicAdapter @Inject constructor() : BasePagedAdapter<Issues, ItemComicsBinding>(object : DiffUtil.ItemCallback<Issues>() {
        override fun areItemsTheSame(currentItem: Issues, nextItem: Issues): Boolean { return currentItem.id == nextItem.id }
        override fun areContentsTheSame(currentItem: Issues, nextItem: Issues): Boolean { return currentItem == nextItem }
    }) {
    override fun itemLayoutResource() = R.layout.item_comics
    override fun createViewHolder(itemView: View) = ComicViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemComicsBinding, dto: Issues, position: Int) {
        binding.setVariable(BR.dto, dto)
        binding.executePendingBindings()
    }
    class ComicViewHolder(itemView: View) : BaseViewHolder<ItemComicsBinding>(itemView)
}

