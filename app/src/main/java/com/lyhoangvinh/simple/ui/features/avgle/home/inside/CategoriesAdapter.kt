package com.lyhoangvinh.simple.ui.features.avgle.home.inside

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.vinh.domain.entities.avgle.Category
import com.lyhoangvinh.simple.databinding.ItemCategoriesBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseListAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import dagger.hilt.android.qualifiers.ActivityContext

class CategoriesAdapter(@ActivityContext context: Context) :
    BaseListAdapter<Category, ItemCategoriesBinding>(context, CategoryDiffCallBack) {

    override fun itemLayoutResource() = R.layout.item_categories

    override fun createViewHolder(itemView: View) = CategoriesViewHolder(itemView)

    private var onClickItemListener: ((Category) -> Unit?)? = null

    fun setOnClickItemListener(onClickItemListener: (Category) -> Unit): CategoriesAdapter {
        this.onClickItemListener = onClickItemListener
        return this
    }

    override fun onBindViewHolder(binding: ItemCategoriesBinding, dto: Category, position: Int) {
        binding.dto = dto
        binding.rlRoot.setOnClickListener { onClickItemListener?.invoke(dto) }
    }

    class CategoriesViewHolder(itemView: View) : BaseViewHolder<ItemCategoriesBinding>(itemView)

    private object CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(currentItem: Category, nextItem: Category): Boolean {
            return currentItem.id == nextItem.id
        }

        override fun areContentsTheSame(currentItem: Category, nextItem: Category): Boolean {
            return currentItem == nextItem
        }
    }
}