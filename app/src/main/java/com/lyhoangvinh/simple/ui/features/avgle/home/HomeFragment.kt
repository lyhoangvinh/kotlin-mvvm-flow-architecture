package com.lyhoangvinh.simple.ui.features.avgle.home

import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentHomeBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelRecyclerViewFragment<FragmentHomeBinding, HomeViewModel, HomeAdapter2>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_home
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        binding.setVariable(BR.vm, viewModel)
        val mWidth =
            (MyApplication.getInstance().getDeviceWidth() - 2 * resources.getDimensionPixelSize(R.dimen.padding_16dp)) / 4 * 3 - resources.getDimensionPixelSize(
                R.dimen.padding_16dp
            ) / 2
        val mHeight = mWidth * 5 / 7
        viewModel.setLayoutParams(mWidth, mHeight, requireActivity())
     }
}