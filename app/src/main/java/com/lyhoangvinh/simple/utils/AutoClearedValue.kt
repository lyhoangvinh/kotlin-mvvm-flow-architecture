package com.lyhoangvinh.simple.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 * @param <T>
</T> */
class AutoClearedValue<T : Any>(fragment: Fragment, value: T) {

    private var valueRef: WeakReference<T>? = WeakReference(value)

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { owner ->
            owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    valueRef?.clear()
                    valueRef = null
                }
            })
        }
    }

    fun get(): T? = valueRef?.get()
}
