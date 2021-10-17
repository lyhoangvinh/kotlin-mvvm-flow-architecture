package com.lyhoangvinh.simple.ui.features.videos.videohome

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.usecases.AddOrDeleteFavoriteUseCase
import com.vinh.domain.usecases.GetVideoHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoHomeViewModel @Inject constructor(
    private val getVideoHome: GetVideoHomeUseCase,
    private val addOrDeleteFavorite: AddOrDeleteFavoriteUseCase
) : BaseListViewModel<VideoHomeAdapter>() {
    override fun fetchData() {

    }

    override fun initAdapter(adapter: VideoHomeAdapter) {
        super.initAdapter(adapter)
        adapter.addClickLike {
            viewModelScope.launch(Dispatchers.IO) {
                addOrDeleteFavorite(it)
                launch(Dispatchers.Main) {
                    adapter.refresh()
                }
            }
        }
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            getVideoHome().execute(onDataSuccess = {
                adapter.submitData(it)
            })
        }
    }
}