package com.lyhoangvinh.simple.ui.features.testsealed

import androidx.fragment.app.viewModels
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentComicBinding
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestSealedFragment : BaseViewModelRecyclerViewFragment<FragmentComicBinding, TestSealedViewModel, TestSealedAdapter>() {
    override val viewModel: TestSealedViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_comic
}