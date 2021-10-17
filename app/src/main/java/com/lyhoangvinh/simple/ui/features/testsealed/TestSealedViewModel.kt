package com.lyhoangvinh.simple.ui.features.testsealed

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.vinh.data.itemviewmodel.ModelListItem
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TestSealedViewModel @Inject constructor() : BaseListViewModel<TestSealedAdapter>() {
    override fun fetchData() {}

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            val data = mutableListOf<ModelListItem>()
            for (i in 0 until 50) {
                if (i == 0) {
                    data.add(ModelListItem.Header)
                } else {
                    data.add(ModelListItem.ContentItem("Item $i"))
                }
            }
            withContext(Dispatchers.Main) {
                adapter.submitList(data)
            }
        }
    }
}