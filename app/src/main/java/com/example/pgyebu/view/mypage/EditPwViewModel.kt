package com.example.pgyebu.view.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService

class EditPwViewModel {

    private val TAG = "EditPwViewModel"
    private val _startMyPageActivityEvent = SingleLiveEvent<Any>()
    val startMyPageActivityEvent: LiveData<Any>
        get() = _startMyPageActivityEvent

    private val _ToastFailureEvent = SingleLiveEvent<Any>()
    val ToastFailureEvent: LiveData<Any>
        get() = _ToastFailureEvent

    var input_origin = MutableLiveData<String>()
    var input_new = MutableLiveData<String>()

    fun onSaveButtonClick() {
        val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)

        val origin = input_origin.value.toString()
        val new = input_new.value.toString()

        ApiService.updateUserPassword(userId, origin, new, operSuccess = {
            _startMyPageActivityEvent.call()
        }, operFailure = {
            _ToastFailureEvent.call()
        })
    }
}