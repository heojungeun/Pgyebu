package com.example.pgyebu.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import java.text.SimpleDateFormat
import java.util.*

class SetStandardViewModel {

    private val TAG = "SetStandardViewModel"
    private val _startSettingActivityEvent = SingleLiveEvent<Any>()
    val startSettingActivityEvent: LiveData<Any>
        get() = _startSettingActivityEvent
    var year = MutableLiveData<Int>()
    var month = MutableLiveData<Int>()
    var day = MutableLiveData<Int>()
    var calendar = Calendar.getInstance()

    init {
//        var calendar = Calendar.getInstance()
        val savedStandard = MyApplication.prefs.getLong(Util.SET_STANDARD_NUM, calendar.time.time)

        setPicker(Date(savedStandard))
//        calendar.time = Date(savedStandard)
//        year.postValue(calendar.get(Calendar.YEAR))
//        month.postValue(calendar.get(Calendar.MONTH))
//        day.postValue(calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun setPicker(date: Date){
        var calendar = Calendar.getInstance()
        calendar.time = date
        year.postValue(calendar.get(Calendar.YEAR))
        month.postValue(calendar.get(Calendar.MONTH))
        day.postValue(calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun onResetButtonClick(){
        MyApplication.prefs.remove(Util.SET_STANDARD_NUM)
        calendar.time = Date()
        setPicker(calendar.time)
        _startSettingActivityEvent.call()
    }

    fun onSaveButtonClick() {
        val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)
        ApiService.setUserSetting(userId, initDay = day.value!!)

        MyApplication.prefs.setLong(Util.SET_STANDARD_NUM,
            SimpleDateFormat("yyyy-MM-dd").parse("${year.value}-${month.value!!.plus(1)}-${day.value}").time)
        _startSettingActivityEvent.call()
    }

}