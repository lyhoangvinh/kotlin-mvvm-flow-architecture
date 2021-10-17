package com.lyhoangvinh.simple.ui.features.comic

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.repo.ComicRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(private val comicRepo: ComicRepo) : BaseListViewModel<ComicAdapter>() {

    override fun fetchData() {
        adapter.refresh()
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            comicRepo.getData().cachedIn(viewModelScope)
                .collectLatest {
                adapter.submitData(it)
            }
        }
    }
}