package com.lyhoangvinh.simple.ui.features.avgle.home.inside

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.vinh.domain.model.entities.avgle.Video
import com.lyhoangvinh.simple.databinding.ItemVideoHomeBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseListAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import dagger.hilt.android.qualifiers.ActivityContext


class VideosHomeAdapter(@ActivityContext context: Context) :
    BaseListAdapter<Video, ItemVideoHomeBinding>(context, DiffCallBack) {


    private var onItemClickListener: ((String) -> Unit)? = null

    private var mWidth = 0

    private var mHeight = 0

    fun setLayoutParams(mWidth: Int, mHeight: Int): VideosHomeAdapter {
        this.mWidth = mWidth
        this.mHeight = mHeight
        return this
    }

    fun setOnItemClickListener(onItemClickListener: (String) -> Unit): VideosHomeAdapter {
        this.onItemClickListener = onItemClickListener
        return this
    }

    override fun itemLayoutResource() = R.layout.item_video_home

    override fun createViewHolder(itemView: View) = VideoViewHolder(itemView)

    override fun onBindViewHolder(binding: ItemVideoHomeBinding, dto: Video, position: Int) {
        binding.imv.layoutParams.width = mWidth
        binding.imv.layoutParams.height = mHeight
        binding.lnlMain.layoutParams = RelativeLayout.LayoutParams(mWidth, RelativeLayout.LayoutParams.WRAP_CONTENT)
        binding.imv.requestLayout()
        binding.video = dto

        binding.tvPreview.setOnClickListener { onItemClickListener?.invoke(dto.previewVideoUrl.toString()) }
        binding.lnlMain.setOnClickListener { onItemClickListener?.invoke(dto.videoUrl.toString()) }
    }

    class VideoViewHolder(itemView: View) : BaseViewHolder<ItemVideoHomeBinding>(itemView)

    private object DiffCallBack : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem.vid == nextItem.vid
        }

        override fun areContentsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem == nextItem
        }
    }
}
