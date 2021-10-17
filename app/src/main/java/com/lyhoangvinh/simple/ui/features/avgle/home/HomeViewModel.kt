package com.lyhoangvinh.simple.ui.features.avgle.home

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.repo.AvgRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val avgRepo: AvgRepo): BaseListViewModel<HomeAdapter>() { override fun fetchData() {}

    fun setLayoutParams(mWidth: Int, mHeight: Int, activity: Activity) {
        adapter.setLayoutParams(mWidth, mHeight, activity)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
//            avgRepo.homeLiveData().withState(true, lifecycleOwner) {
//                adapter.submitList(it)
//            }
            avgRepo.getHomeData().execute(true) {
                adapter.submitList(it)
            }
        }
    }
}