package com.lyhoangvinh.simple.data.network


/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repo classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 */

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

//sealed class Status {
//    object ERROR : Status()
//    object LOADING : Status()
//    object SUCCESS: Status()
//}