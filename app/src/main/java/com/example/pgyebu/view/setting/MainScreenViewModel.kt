package com.example.pgyebu.view.setting

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import kotlin.properties.Delegates

class MainScreenViewModel {

    private val TAG = "MainScreenViewModel"
    private val _startSettingActivityEvent = SingleLiveEvent<Any>()
    val startSettingActivityEvent: LiveData<Any>
        get() = _startSettingActivityEvent

    var key = 0
    var keyName = ""
    var checked_radioButton = MutableLiveData<Int>()
    init {
        key = MyApplication.prefs.getInt(Util.SET_MAINSCREEN_NUM,  R.id.radio_cur_spend)
        keyName = MyApplication.prefs.getString(Util.SET_MAINSCREEN_NAME, Util.RADIO_MAINSCREEN[R.id.radio_cur_spend]!!)
        checked_radioButton.postValue(key)
        println("$TAG: - 현재 메인화면 출력 옵션은 $key - $keyName")
    }

    fun selectOption(radioButton: View){
        keyName = (radioButton as RadioButton).text.toString()
        key = radioButton.id
        println("$TAG: click radio $key")
    }

    fun onSaveButtonClick() {
        val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)
        ApiService.setUserSetting(userId, displayOption = Util.RADIO_MAINSCREEN_API[key]!!)
        MyApplication.prefs.setString(Util.SET_MAINSCREEN_NAME, keyName)
        MyApplication.prefs.setInt(Util.SET_MAINSCREEN_NUM, key)
        _startSettingActivityEvent.call()
    }
}