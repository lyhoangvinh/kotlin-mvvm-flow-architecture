package com.lyhoangvinh.simple.ui.features.videos.videohome

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.model.State
import com.vinh.domain.usecases.AddOrDeleteFavoriteUseCase
import com.vinh.domain.usecases.GetVideoHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class VideoHomeViewModel @Inject constructor(
    private val getVideoHome: GetVideoHomeUseCase,
    private val addOrDeleteFavorite: AddOrDeleteFavoriteUseCase
) : BaseListViewModel<VideoHomeAdapter>() {

    override fun fetchData() {
        adapter.refresh()
    }

    override fun initAdapter(adapter: VideoHomeAdapter) {
        super.initAdapter(adapter)
        adapter.addClickLike { video, cbChange ->
            viewModelScope.launch(Dispatchers.IO) {
                publishState(State.loading())
                addOrDeleteFavorite(video)
                withContext(Dispatchers.Main) {
                    cbChange()
                    adapter.refresh()
                    publishState(State.success())
                }
            }
        }
    }
    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            getVideoHome().cachedIn(viewModelScope).execute(onDataSuccess = { adapter.submitData(it) })
        }
    }
}