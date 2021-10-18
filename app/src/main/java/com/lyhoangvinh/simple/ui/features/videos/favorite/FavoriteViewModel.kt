package com.lyhoangvinh.simple.ui.features.videos.favorite

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.usecases.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseListViewModel<FavoriteAdapter>() {

    override fun fetchData() {
        adapter.refresh()
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            getFavoriteUseCase().execute(onDataSuccess = { adapter.submitData(it) })
        }
    }
}

