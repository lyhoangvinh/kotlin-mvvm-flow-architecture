package com.lyhoangvinh.simple.ui.features.comic2

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.extension.observe
import javax.inject.Inject

class ComicViewModel2 @ViewModelInject constructor(private val comicRepo: ComicRepo, val comicObservable: ComicObservable) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            launchOnViewModelScope { comicRepo.getDataSanwit() }.observe(lifecycleOwner) {
                publishState(it.state)
                if (it.state == State.success()) comicObservable.notifyContent(it.toString())
            }
        }
    }

    class ComicObservable @Inject constructor() : BaseObservable() {

        @Bindable
        var content : String?= ""

        fun notifyContent(content : String) {
            this.content = content
            notifyPropertyChanged(BR.content)
        }
    }
}