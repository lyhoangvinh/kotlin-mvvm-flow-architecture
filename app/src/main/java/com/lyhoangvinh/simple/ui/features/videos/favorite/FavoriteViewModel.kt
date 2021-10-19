package com.lyhoangvinh.simple.ui.features.videos.favorite

import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListViewModel
import com.vinh.domain.model.State
import com.vinh.domain.usecases.DeleteFavoriteUseCase
import com.vinh.domain.usecases.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavorite: GetFavoriteUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
) : BaseListViewModel<FavoriteAdapter>() {

    val checkViewVisibility = ObservableBoolean(true)

    override fun fetchData() {
        viewModelScope.launch {
            getFavorite.resetChecked()
            withContext(Dispatchers.Main) {
                publishState(State.success())
            }
        }
    }

    override fun initAdapter(adapter: FavoriteAdapter) {
        super.initAdapter(adapter)
        adapter.addOnClick { favorite, checkChange ->
            viewModelScope.launch(Dispatchers.IO) {
                getFavorite.setCheckedChange(favorite)
                withContext(Dispatchers.Main) {
                    checkChange()
                }
            }
        }
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            getFavorite().execute(onDataSuccess = {
                checkViewVisibility.set(it.checkedViewVisibility)
                adapter.submitList(it.favoriteItems)
            })
        }
    }

    fun delete() {
        viewModelScope.launch {
            deleteFavorite(getFavorite.getCheckedValue())
            withContext(Dispatchers.Main) {
                getFavorite.resetChecked()
            }
        }
    }
}

