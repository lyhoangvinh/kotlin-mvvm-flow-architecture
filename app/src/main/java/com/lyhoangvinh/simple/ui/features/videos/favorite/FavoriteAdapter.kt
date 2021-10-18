package com.lyhoangvinh.simple.ui.features.videos.favorite

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ItemFavoriteBinding
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.vinh.domain.entities.avgle.Favorite
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() :
    BasePagedAdapter<Favorite, ItemFavoriteBinding>(object :
        DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
            oldItem == newItem
    }) {

    override fun itemLayoutResource() = R.layout.item_favorite

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemFavoriteBinding> =
        object : BaseViewHolder<ItemFavoriteBinding>(itemView) {}

    override fun onBindViewHolder(binding: ItemFavoriteBinding, dto: Favorite, position: Int) {
        binding.setVariable(BR.dto, dto)
    }
}