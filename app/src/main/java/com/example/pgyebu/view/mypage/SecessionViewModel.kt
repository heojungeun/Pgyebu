package com.example.pgyebu.view.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService

class SecessionViewModel {

    private val TAG = "SecessionViewModel"
    private val _startMyPageActivityEvent = SingleLiveEvent<Any>()
    val startMyPageActivityEvent: LiveData<Any>
        get() = _startMyPageActivityEvent

    var input_id = MutableLiveData<String>()

    fun onSaveButtonClick() {
        val userid = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)
        ApiService.deleteUser(userid) {
            MyApplication.prefs.setString(Util.IS_LOGIN, Util.NOLOGIN)
            _startMyPageActivityEvent.call()
        }

    }
}