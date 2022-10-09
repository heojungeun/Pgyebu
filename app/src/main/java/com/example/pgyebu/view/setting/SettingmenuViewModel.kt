package com.example.pgyebu.view.setting

import androidx.lifecycle.LiveData
import com.example.pgyebu.event.SingleLiveEvent

class SettingmenuViewModel {

    private val TAG = "SettingmenuViewModel"
    private val _startSetStandardActivityEvent = SingleLiveEvent<Any>()
    val startSetStandardActivityEvent: LiveData<Any>
        get() = _startSetStandardActivityEvent

    private val _startMainScreenActivityEvent = SingleLiveEvent<Any>()
    val startMainScreenActivityEvent: LiveData<Any>
        get() = _startMainScreenActivityEvent

    private val _startHomeActivityEvent = SingleLiveEvent<Any>()
    val startHomeActivityEvent: LiveData<Any>
        get() = _startHomeActivityEvent

    fun onSetStandardButtonClick() {
        _startSetStandardActivityEvent.call()
    }

    fun onMainScreenButtonClick() {
        _startMainScreenActivityEvent.call()
    }

    fun onHomeButtonClick() {
        _startHomeActivityEvent.call()
    }
}