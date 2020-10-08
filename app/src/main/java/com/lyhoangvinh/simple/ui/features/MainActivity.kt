package com.lyhoangvinh.simple.ui.features

import androidx.activity.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityMainBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseActivity
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutResource() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()
    override fun onBackPressed() {
        if (!findNavController(R.id.navHostFragment).popBackStack()) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}