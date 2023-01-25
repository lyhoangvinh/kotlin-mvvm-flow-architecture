package com.lyhoangvinh.simple.utils


import com.vinh.data.manager.SharedPrefs
import com.vinh.data.utils.BaseSharedPreferenceLiveData
import javax.inject.Inject

class OptionSharedPreferenceLiveData @Inject constructor(sharedPrefs: SharedPrefs) :
    BaseSharedPreferenceLiveData<Int>(
        sharedPrefs,
        Constants.OPTIONS, 0
    ) {
    override fun getValueFromPreferences(key: String, defValue: Int) =
        sharedPrefs!![Constants.OPTIONS, Int::class.java]

}