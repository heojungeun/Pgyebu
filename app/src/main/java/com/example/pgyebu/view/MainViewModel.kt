package com.example.pgyebu.view

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.event.SingleLiveEvent

class MainViewModel {

    private val TAG = "MainViewModel"
    private val _startSignUpActivityEvent = SingleLiveEvent<Any>()
    val startSignUpActivityEvent: LiveData<Any>
        get() = _startSignUpActivityEvent

    private val _startLogInActivityEvent = SingleLiveEvent<Any>()
    val startLogInActivityEvent: LiveData<Any>
        get() = _startLogInActivityEvent

    private val _startPwSearchActivityEvent = SingleLiveEvent<Any>()
    val startPwSearchActivityEvent: LiveData<Any>
        get() = _startPwSearchActivityEvent

    val input_id = MutableLiveData<String>()
    val input_pw = MutableLiveData<String>()

    fun onLoginButtonClick() {
        _startLogInActivityEvent.call()
    }

    fun onSignUpButtonClick() {
        _startSignUpActivityEvent.call()
    }

    fun onPwSearchButtonClick() {
        _startPwSearchActivityEvent.call()
    }
}