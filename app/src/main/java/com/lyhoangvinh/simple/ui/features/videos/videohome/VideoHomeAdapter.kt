package com.lyhoangvinh.simple.ui.features.videos.videohome

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ItemVideosHome2Binding
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.vinh.domain.model.entities.avgle.Video
import javax.inject.Inject

class VideoHomeAdapter @Inject constructor() :
    BasePagedAdapter<Video, ItemVideosHome2Binding>(object :
        DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.vid == newItem.vid

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }) {

    private var onClickLike: ((Video, ()-> Unit) -> Unit)? = null

    fun addClickLike(onClickLike: ((Video, ()-> Unit) -> Unit)? = null) {
        this.onClickLike = onClickLike
    }

    override fun itemLayoutResource() = R.layout.item_videos_home_2

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemVideosHome2Binding> =
        object : BaseViewHolder<ItemVideosHome2Binding>(itemView) {}.apply {
            binding.cbFavorite.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {
                    val favorite = it.favorite?:false
                    onClickLike?.invoke(it) {
                        binding.cbFavorite.setCheckMarkDrawable(if (!favorite) R.drawable.favorite else R.drawable.add_favorite)
                    }
                }
            }
        }

    override fun onBindViewHolder(binding: ItemVideosHome2Binding, dto: Video, position: Int) {
        binding.setVariable(BR.dto, dto)
    }
}