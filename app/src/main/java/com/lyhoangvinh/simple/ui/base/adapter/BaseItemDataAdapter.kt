package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.qualifiers.ActivityContext

abstract class BaseItemDataAdapter(@ActivityContext val context: Context, diffUtil: DiffUtil.ItemCallback<ItemData>) : ListAdapter<ItemData,  BaseItemDataViewHolder<ItemData, ViewDataBinding>>(diffUtil){
    override fun getItemViewType(position: Int) = setItemViewType(getItem(position))
    override fun onBindViewHolder(holder: BaseItemDataViewHolder<ItemData, ViewDataBinding>, position: Int) {
        holder.setItem(getItem(position), holder.binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemDataViewHolder<ItemData, ViewDataBinding>
    = createItemViewHolder(LayoutInflater.from(context).inflate(getLayoutResource(viewType), parent, false), viewType)

    abstract fun createItemViewHolder(view: View, viewType: Int): BaseItemDataViewHolder<ItemData, ViewDataBinding>
    abstract fun getLayoutResource(viewType: Int): Int
    abstract fun setItemViewType(item: ItemData): Int
}

abstract class BaseItemDataViewHolder<T: ItemData, B : ViewDataBinding>(view: View) :
    BaseViewHolder<B>(view) {

    private lateinit var data: T

    open fun setItem(data: T, binding: B) {
        this.data = data
        this.binding = binding
    }
}