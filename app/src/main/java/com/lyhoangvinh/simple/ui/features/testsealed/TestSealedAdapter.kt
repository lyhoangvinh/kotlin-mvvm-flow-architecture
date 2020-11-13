package com.lyhoangvinh.simple.ui.features.testsealed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lyhoangvinh.simple.R
import com.vinh.data.itemviewmodel.ModelListItem
import com.lyhoangvinh.simple.databinding.ItemContentBinding
import com.lyhoangvinh.simple.utils.extension.ordinal
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class TestSealedAdapter @Inject constructor(@ActivityContext val context: Context) : ListAdapter<ModelListItem, TestSealedAdapter.ModelViewHolder>(object : DiffUtil.ItemCallback<ModelListItem>() {
    override fun areItemsTheSame(oldItem: ModelListItem, newItem: ModelListItem) = oldItem == newItem
    override fun areContentsTheSame(oldItem: ModelListItem, newItem: ModelListItem) = oldItem == newItem }) {
    override fun getItemViewType(position: Int): Int = getItem(position).ordinal()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return when (viewType) {
            ModelListItem.Header.ordinal() -> ModelViewHolder.HeaderViewHolder(context, parent)
            ModelListItem.ContentItem.ordinal() -> ModelViewHolder.ContentViewHolder(context, parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if (holder is ModelViewHolder.ContentViewHolder) {
            val binding = DataBindingUtil.bind<ItemContentBinding>(holder.itemView)
            binding?.tvContent?.text = (getItem(position) as ModelListItem.ContentItem).content
        }
    }

    sealed class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        class HeaderViewHolder(context: Context, parent: ViewGroup) : ModelViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_header, parent, false)
        )

        class ContentViewHolder(context: Context, parent: ViewGroup) : ModelViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_content, parent, false)
        )
    }
}