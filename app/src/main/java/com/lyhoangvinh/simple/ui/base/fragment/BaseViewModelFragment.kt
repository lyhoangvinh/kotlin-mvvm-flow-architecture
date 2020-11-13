package com.lyhoangvinh.simple.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.ui.base.activity.BaseActivity
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.vinh.domain.model.State
import com.vinh.domain.model.Status

/**
 * Base fragment class that has a ViewModel extending [BaseViewModel].
 * All fragments extend this fragment must be added into [BaseActivity]
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through [BaseViewModel.stateLiveData]
 */

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseFragment<B>() {

    protected abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity !is BaseActivity) {
            throw IllegalStateException("All fragment's container must extend BaseActivity")
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view, activity)
        viewModel.onCreate(this, getFragmentArguments())
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { handleState(it) })
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(state: State?) {
        setLoading(state != null && state.status == Status.LOADING)
    }

    open fun setLoading(loading: Boolean) {
        (activity as BaseActivity).setLoading(loading)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }
}