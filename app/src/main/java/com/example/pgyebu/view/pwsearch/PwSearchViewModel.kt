package com.example.pgyebu.view.pwsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService

class PwSearchViewModel {
    private val TAG = "PwSearchViewModel"
//    private val _startActivityEvent = SingleLiveEvent<Any>()
//    val startActivityEvent: LiveData<Any>
//        get() = _startActivityEvent
    var input_user_id = MutableLiveData<String>()
    var input_user_email = MutableLiveData<String>()
    var userPw = MutableLiveData<String>()

//    fun onSearchButtonClick() {
//        _startActivityEvent.call()
//    }

    fun getPwSearch(){
        val userId = input_user_id.value.toString()
        val userEmail = input_user_email.value.toString()
        ApiService.getPwSearch(userId, userEmail, operSuccess = { password ->
            userPw.postValue("현재 비밀번호: $password")
        })
    }
}