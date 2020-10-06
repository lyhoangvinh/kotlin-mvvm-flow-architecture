package com.lyhoangvinh.simple.ui.features.splash

import androidx.activity.viewModels
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivitySplashBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModels()
    override fun getLayoutResource() = R.layout.activity_splash
}