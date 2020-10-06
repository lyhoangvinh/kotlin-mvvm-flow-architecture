package com.lyhoangvinh.simple.ui.base.activity

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.extension.observe
import javax.inject.Inject

abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    protected lateinit var binding: B

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::binding.isInitialized.not()) {
            binding = DataBindingUtil.setContentView(this, getLayoutResource())
            binding.apply {
//                setVariable(BR.viewModel, viewModel)
                root.isClickable = true
                executePendingBindings()
            }
        }
        binding.lifecycleOwner = this
        viewModel.onCreate(this, intent.extras)
        viewModel.stateLiveData.observe(this) { handleState(it) }
    }

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(state: State?) {
        setLoading(state != null && state.status == Status.LOADING)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyView()
    }

    override fun shouldUseDataBinding() = true
}