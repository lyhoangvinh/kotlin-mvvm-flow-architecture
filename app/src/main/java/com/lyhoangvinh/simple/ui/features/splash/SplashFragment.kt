package com.lyhoangvinh.simple.ui.features.splash

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentSplashBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseViewModelFragment<FragmentSplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_splash
    override fun initialize(view: View, ctx: Context?) {
        binding.btnGetApi.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toComic2())
        }
        binding.btnRcv.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toComic())
        }

        binding.btnAvg.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toHomeAvg())
        }
        binding.btnTestSealedClass.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toTestSealed())
        }

        binding.btnVideo.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toVideo())
        }

        binding.btnVideoRemoteMediator.setOnClickListener {
            getNavController()?.navigate(SplashFragmentDirections.toVideoInDB())
        }
    }
}