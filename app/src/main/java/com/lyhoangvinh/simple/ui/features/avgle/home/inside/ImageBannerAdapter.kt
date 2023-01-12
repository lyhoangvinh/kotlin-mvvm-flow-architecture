package com.lyhoangvinh.simple.ui.features.avgle.home.inside

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.vinh.domain.model.entities.avgle.Collection

class ImageBannerAdapter(fm: FragmentManager, private var mBannerList: List<Collection>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return BannerImagesFragment.getInstance(mBannerList[position])
    }

    override fun getCount(): Int {
        return mBannerList.size
    }
}