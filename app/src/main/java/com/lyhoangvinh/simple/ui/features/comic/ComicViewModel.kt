package com.lyhoangvinh.simple.ui.features.comic

import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ComicViewModel @ViewModelInject constructor(private val comicRepo: ComicRepo) : BaseListViewModel<ComicAdapter>() {

    override fun fetchData() {
        adapter.refresh()
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            comicRepo.getData().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}