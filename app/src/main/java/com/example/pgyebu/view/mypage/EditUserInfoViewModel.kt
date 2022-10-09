package com.example.pgyebu.view.mypage

import androidx.core.text.isDigitsOnly
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import java.time.LocalDateTime

class EditUserInfoViewModel {

    private val TAG = "EditUserInfoViewModel"
    private val _startMyPageActivityEvent = SingleLiveEvent<Any>()
    val startMyPageActivityEvent: LiveData<Any>
        get() = _startMyPageActivityEvent

    var input_name = MutableLiveData<String>()
    var input_email = MutableLiveData<String>()
    var input_birthdate = MutableLiveData<String>()

    fun onSaveButtonClick() {
        val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)

        var name = input_name.value.toString()
        if(name.isNullOrEmpty() || name == "null"){
            name = ""
        }
        var email = input_email.value.toString()
        if(email.isNullOrEmpty() || email == "null"){
            email = ""
        }
        var birth = input_birthdate.value.toString()
        if (birth.count() == 8){
            val month = birth.subSequence(4,6).toString().toInt()
            val day = birth.subSequence(6,8).toString().toInt()
            if (month > 12 || day > 31) {
                println("$TAG: - 유효하지 않은 날짜입니다.")
                return
            }
            birth = "${birth.subSequence(0,4)}-${birth.subSequence(4,6)}-${birth.subSequence(6,8)}"
        } else if(birth.isNullOrEmpty() || birth == "null"){
            birth = ""
        } else{
            println("$TAG: - 생년월일(YYYYMMDD) 올바르지 않은 형식입니다.")
            return
        }

        ApiService.updateUserInfo(userId, name, email, birth, operSuccess = {
            _startMyPageActivityEvent.call()
        })
    }
}