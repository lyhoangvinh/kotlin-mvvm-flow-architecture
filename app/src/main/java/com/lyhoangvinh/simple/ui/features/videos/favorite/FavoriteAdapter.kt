package com.lyhoangvinh.simple.ui.features.videos.favorite

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ItemFavoriteBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseListAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.vinh.domain.entities.FavoriteItem
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(@ActivityContext context: Context) :
    BaseListAdapter<FavoriteItem, ItemFavoriteBinding>(context, object :
        DiffUtil.ItemCallback<FavoriteItem>() {
        override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean =
            oldItem.favorite.id == newItem.favorite.id

        override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean =
            oldItem == newItem
    }) {

    private var onClickChecked: ((FavoriteItem, ()-> Unit) -> Unit)? = null

    fun addOnClick(onClickChecked: ((FavoriteItem, ()-> Unit) -> Unit)?) {
        this.onClickChecked = onClickChecked
    }

    override fun itemLayoutResource() = R.layout.item_favorite

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemFavoriteBinding> =
        object : BaseViewHolder<ItemFavoriteBinding>(itemView) {}.apply {
            binding.cb.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { item ->
                    val isChecked = item.isChecked ?: false
                    onClickChecked?.invoke(item) {
                        binding.cb.isChecked = !isChecked
                    }
                }
            }
        }

    override fun onBindViewHolder(binding: ItemFavoriteBinding, dto: FavoriteItem, position: Int) {
        binding.setVariable(BR.dto, dto)
    }
}