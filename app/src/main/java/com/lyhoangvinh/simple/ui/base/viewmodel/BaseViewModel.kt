package com.lyhoangvinh.simple.ui.base.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.lyhoangvinh.simple.utils.extension.observe
import com.lyhoangvinh.simple.utils.livedata.SafeMutableLiveData
import com.vinh.data.source.BaseDataSource
import com.vinh.domain.model.Resource
import com.vinh.domain.model.State
import com.vinh.domain.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


abstract class BaseViewModel : ViewModel() {

    @NonNull
    val stateLiveData = SafeMutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    protected lateinit var lifecycleOwner: LifecycleOwner

    /**
     * called after fragment / activity is created with input bundle arguments
     * @param bundle argument data
     */
    @CallSuper
    fun onCreate(
        lifecycleOwner: LifecycleOwner,
        bundle: Bundle?
    ) {
        this.lifecycleOwner = lifecycleOwner
        if (isFirstTimeUiCreate) {
            onFirstTimeUiCreate(lifecycleOwner, bundle)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     * @param bundle
     */
    abstract fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?)

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    open fun onDestroyView() {
        isFirstTimeUiCreate = true
    }

    internal fun <T> launchOnViewModelScope(block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    @MainThread
    protected fun publishState(state: State) {
        stateLiveData.setValue(state)
    }

    fun <T> LiveData<Resource<T>>?.withState(
        showProgress: Boolean,
        viewLifecycleOwner: LifecycleOwner,
        callBack: (data: T?) -> Unit
    ) {
        this.observe(viewLifecycleOwner) {
            if (showProgress) publishState(it.state)
            if (it.state == State.success()) callBack.invoke(it.data)
        }
    }

    fun <T> LiveData<Resource<T>>?.withState2(
        showProgress: Boolean,
        viewLifecycleOwner: LifecycleOwner,
        callBack: (data: T?) -> Unit
    ) {
        this.observe(viewLifecycleOwner) {
            if (showProgress) publishState(it.state)
            callBack.invoke(it.data)
        }
    }

    /**
     * Add and execute an resource flowable created by
     * see [com.vinh.data.source.BaseDataSource]
     * Loading, error, success status will be updated automatically via [.stateLiveData] which should be observed
     * by fragments / activities to update UI appropriately
     * @param showProgress true if should show progress when executing, false if not
     * @param onDataSuccess consume response data
     * @param <T> type of response
    </T> */
    suspend fun <T> Flow<Resource<T>>.execute(showProgress: Boolean, onDataSuccess: (T?) -> Unit, onDataError: ((String) -> Unit)? = null) =
        catch { cause ->
            /*
              val message = ErrorEntity.getError(it).getMessage().orEmpty()
                    publishState(State.error(message))
                    errorConsumer?.accept(message)
             */
            onDataError?.invoke(cause.message.orEmpty())
        }.collect {
            Log.i("source", "source addRequest: resource changed: $it")
            if (it.data != null) {
                onDataSuccess.invoke(it.data)
            }
            if (it.status == Status.LOADING && !showProgress) {
                // do nothing if progress showing is not allowed
            } else {
                publishState(it.state)
                if (it.status == Status.ERROR) onDataError?.invoke(it.message.orEmpty())
            }
        }

    suspend fun <T> Flow<Resource<T>>.execute(showProgress: Boolean, onDataSuccess: (T?) -> Unit) =
        execute(showProgress, onDataSuccess, null)
}