package com.lyhoangvinh.simple.ui.features.videos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lyhoangvinh.simple.ui.features.videos.favorite.FavoriteFragment
import com.lyhoangvinh.simple.ui.features.videos.videohome.VideoHomeFragment
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
class VideosPagerAdapter @Inject constructor(fragment: Fragment)
    : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VideoHomeFragment()
            else -> FavoriteFragment()
        }
    }

    override fun getItemCount(): Int {
        return ITEM_SIZE
    }

    companion object {
        const val ITEM_SIZE = 2
    }
}