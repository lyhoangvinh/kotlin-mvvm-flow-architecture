package com.vinh.data.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class RefreshableLiveData<T>(
    private val source: () -> LiveData<T>
) : MediatorLiveData<T>() {

    private var liveData = source()

    init {
        this.addSource(liveData, ::observer)
    }

    private fun observer(data: T) {
        value = data
    }

    fun refresh() {
        this.removeSource(liveData)
        liveData = source()
        this.addSource(liveData, ::observer)
    }
}


abstract class ApiLiveData<T>: MediatorLiveData<T>() {

    private var source: (() -> LiveData<T>)?=null

    fun setSource(source: () -> LiveData<T>) {
        this.source = source
        this.addSource(source(), ::observer)
    }


    private fun observer(data: T) {
        value = data
    }

    fun refresh() {
        source?.let {
            removeSource(it())
            addSource(it(), ::observer)
        }
    }
}
