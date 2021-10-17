package com.lyhoangvinh.simple.ui.features.videos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lyhoangvinh.simple.ui.features.videos.videohome.VideoHomeFragment
import javax.inject.Inject

class VideosPagerAdapter @Inject constructor(fragment: Fragment)
    : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            else -> VideoHomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return ITEM_SIZE
    }

    companion object {
        const val ITEM_SIZE = 2
    }
}