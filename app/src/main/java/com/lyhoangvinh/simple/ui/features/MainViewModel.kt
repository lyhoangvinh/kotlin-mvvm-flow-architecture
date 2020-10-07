package com.lyhoangvinh.simple.ui.features

import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel

class MainViewModel @ViewModelInject constructor(): BaseViewModel() {
    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {

    }
}