package com.example.pgyebu.view.mypage

import androidx.lifecycle.LiveData
import com.example.pgyebu.event.SingleLiveEvent

class MypagemenuViewModel {

    private val TAG = "MypagemenuViewModel"
    private val _startEditPwActivityEvent = SingleLiveEvent<Any>()
    val startEditPwActivityEvent: LiveData<Any>
        get() = _startEditPwActivityEvent

    private val _startHomeActivityEvent = SingleLiveEvent<Any>()
    val startHomeActivityEvent: LiveData<Any>
        get() = _startHomeActivityEvent

    private val _startEditUserInfoActivityEvent = SingleLiveEvent<Any>()
    val startEditUserInfoActivityEvent: LiveData<Any>
        get() = _startEditUserInfoActivityEvent

    private val _startSecessionActivityEvent = SingleLiveEvent<Any>()
    val startSecessionActivityEvent: LiveData<Any>
        get() = _startSecessionActivityEvent

    fun onEditPwButtonClick() {
        _startEditPwActivityEvent.call()
    }

    fun onEditUserInfoButtonClick() {
        _startEditUserInfoActivityEvent.call()
    }

    fun onSecessionButtonClick() {
        _startSecessionActivityEvent.call()
    }

    fun onHomeButtonClick() {
        _startHomeActivityEvent.call()
    }
}