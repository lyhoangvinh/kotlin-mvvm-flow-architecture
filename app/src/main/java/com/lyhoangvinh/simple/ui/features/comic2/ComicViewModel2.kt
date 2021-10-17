package com.lyhoangvinh.simple.ui.features.comic2

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.vinh.domain.repo.ComicRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComicViewModel2 @Inject constructor(private val comicRepo: ComicRepo, val comicObservable: ComicObservable) : BaseViewModel() {

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
//            comicRepo.getData2().execute(true) {
//                val data = Thread.currentThread().name + "\n " + it.toString()
//                comicObservable.notifyContent(data)
//            }

//            comicRepo.getData6().execute(true, onDataSuccess =  {
//                val data = Thread.currentThread().name + "\n " + it.toString()
//                comicObservable.notifyContent(data)
//            }, onDataError =  {
//                Log.d("EROOR23123", it)
//            })

            comicRepo.getData4().withState2(true, lifecycleOwner) {
                comicObservable.notifyContent(it.toString())
            }

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