package com.lyhoangvinh.simple.ui.features.videos

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentVideoBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseFragment
import com.lyhoangvinh.simple.utils.extension.toColor
import com.vinh.domain.usecases.GetFavoriteCountUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@AndroidEntryPoint
class VideosFragment : BaseFragment<FragmentVideoBinding>() {
    @FlowPreview
    @Inject
    lateinit var pagerAdapter: VideosPagerAdapter
    @Inject
    lateinit var getFavoriteCount: GetFavoriteCountUseCase

    override fun getLayoutResource() = R.layout.fragment_video
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = pagerAdapter
        var tvBadge: TextView? = null
        var imvFavorite: ImageView? = null
        var tvTitle: TextView? = null
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            var title = R.string.tab_favorite
            var icon = R.drawable.favorite
            when (position) {
                0 -> {
                    title = R.string.tab_home
                    icon = R.drawable.ic_home_black_24dp
                }
                1 -> {
                    tab.setCustomView(R.layout.view_badge_number)
                    tvBadge = tab.customView?.findViewById(R.id.tvBadge)
                    imvFavorite = tab.customView?.findViewById(R.id.imvLogo)
                    tvTitle = tab.customView?.findViewById(R.id.tvTitle)
                    imvFavorite?.setColorFilter(
                        R.color.colorTextHint.toColor(requireActivity()),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }
            tab.text = getText(title)
            tab.icon = getDrawable(requireActivity(), icon)
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = R.color.colorTextHint.toColor(requireActivity())
                if (tab?.position == 2) {
                    imvFavorite?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                    tvTitle?.setTextColor(tabIconColor)
                } else {
                    tab?.icon?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = R.color.colorPrimary.toColor(requireActivity())
                if (tab?.position == 2) {
                    imvFavorite?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                    tvTitle?.setTextColor(tabIconColor)
                } else {
                    tab?.icon?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                }
            }
        })

        getFavoriteCount().observe(viewLifecycleOwner) {
            tvBadge?.text = it.toString()
        }
    }
}