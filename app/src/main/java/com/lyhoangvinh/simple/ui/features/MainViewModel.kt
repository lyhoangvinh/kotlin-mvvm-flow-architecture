package com.lyhoangvinh.simple.ui.features

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {
    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {

    }
}