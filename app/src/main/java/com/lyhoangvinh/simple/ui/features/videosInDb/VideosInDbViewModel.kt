package com.lyhoangvinh.simple.ui.features.videosInDb

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.lyhoangvinh.simple.ui.features.videos.favorite.FavoriteAdapter
import com.lyhoangvinh.simple.ui.features.videos.videohome.VideoHomeAdapter
import com.vinh.domain.usecases.GetVideoPagingSourceByExecutor
import com.vinh.domain.usecases.UpdateVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class VideosInDbViewModel @Inject constructor(
    private val updateVideo: UpdateVideoUseCase,
    private val getVideoPagingSourceByExecutor: GetVideoPagingSourceByExecutor
) : BaseListViewModel<VideoHomeAdapter>() {
    override fun fetchData() {
        adapter.refresh()
    }

    override fun initAdapter(adapter: VideoHomeAdapter) {
        super.initAdapter(adapter)
        adapter.addClickLike { video, _  ->
            viewModelScope.launch(Dispatchers.IO) {
                val currentFavorite = video.favorite ?: false
                updateVideo(video.copy(favorite = !currentFavorite))
//                launch(Dispatchers.Main) {
//                    onChange()
//                }
            }
        }
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenResumed {
            getVideoPagingSourceByExecutor("VINH").cachedIn(viewModelScope)
                .collectLatest { adapter.submitData(it) }
        }
    }
}