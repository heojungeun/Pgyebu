package com.example.pgyebu.view.signup

import androidx.lifecycle.LiveData
import com.example.pgyebu.event.SingleLiveEvent

class SignUpViewModel {

    private val TAG = "SignUpViewModel"
    private val _startSignUpActivityEvent = SingleLiveEvent<Any>()
    val startSignUpActivityEvent: LiveData<Any>
        get() = _startSignUpActivityEvent

    fun onSignupButtonClick() {
        _startSignUpActivityEvent.call()
    }
}