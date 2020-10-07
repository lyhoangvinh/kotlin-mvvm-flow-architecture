package com.lyhoangvinh.simple.ui.features.error

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.activity.BaseActivity
import com.lyhoangvinh.simple.utils.Constants
import kotlinx.android.synthetic.main.activity_error.*

class ErrorActivity : BaseActivity() {
    override fun getLayoutResource() = R.layout.activity_error
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.getStringExtra(Constants.EXTRA_DATA)?.let {
            tvError.text = it
        }
    }
}