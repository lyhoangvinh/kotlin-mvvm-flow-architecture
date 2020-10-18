package com.lyhoangvinh.simple.ui.features.comic2

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.extension.observe
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ComicViewModel2 @ViewModelInject constructor(private val comicRepo: ComicRepo, val comicObservable: ComicObservable) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
//            launchOnViewModelScope { comicRepo.getDataSandwich() }.observe(lifecycleOwner) {
//                publishState(it.state)
//                if (it.state == State.success()) comicObservable.notifyContent(it.toString())
//            }
//            comicRepo.getData2().collect {
//                publishState(it.state)
//                comicObservable.notifyContent(it.toString())
//            }
            comicRepo.getData2().execute {
                comicObservable.notifyContent(it.toString())
            }

//            comicRepo.getData4().withState2(lifecycleOwner) {
//                comicObservable.notifyContent(it.toString())
//            }

//        }
//        viewModelScope.launch {
//            try {
//                val data = comicRepo.getData5()
//                comicObservable.notifyContent(data.body().toString())
//            }catch (ex: Exception) {
//                comicObservable.notifyContent(ex.message.toString())
//            }
        }
    }

    fun onReSet() {
        comicRepo.refresh()
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