package com.example.pgyebu.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.R
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.entity.EcoEvent
import com.example.pgyebu.entity.EcoEventList
import com.example.pgyebu.event.ListLiveData
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import com.example.pgyebu.network.EcoEventObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel {

    private val TAG = "HomeViewModel"
    private val _startAddActivityEvent = SingleLiveEvent<Any>()
    val startAddActivityEvent: LiveData<Any>
        get() = _startAddActivityEvent

    private val _startChartActivityEvent = SingleLiveEvent<Any>()
    val startChartActivityEvent: LiveData<Any>
        get() = _startChartActivityEvent

    private val _startMypagemenuActivityEvent = SingleLiveEvent<Any>()
    val startMypagemenuActivityEvent: LiveData<Any>
        get() = _startMypagemenuActivityEvent

    private val _startSettingActivityEvent = SingleLiveEvent<Any>()
    val startSettingActivityEvent: LiveData<Any>
        get() = _startSettingActivityEvent

    var displayMainStatic = MutableLiveData<String>()
    var displayOption = MutableLiveData<String>()

    var finList = ListLiveData<EcoEvent>()

    val userId = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)

    fun addItem(item: EcoEvent) {
        finList.add(item)
    }

    fun addAllItem(itemList: List<EcoEvent>) {
        finList.addAll(itemList)
    }

    fun removeItem(item: EcoEvent) {
        finList.remove(item)
    }

    fun onAddButtonClick() {
        _startAddActivityEvent.call()
    }

    fun onChartButtonClick() {
        _startChartActivityEvent.call()
    }

    fun onMypagemenuButtonClick() {
        _startMypagemenuActivityEvent.call()
    }

    fun onSettingButtonClick() {
        _startSettingActivityEvent.call()
    }

    fun getDisplayMainStatics(){
        ApiService.getDisplayMainStatics(userId, operSuccess = { result ->
            /*  1. expenditure
                2. income - expenditure
                3. balance
            * */
            // 메인 선택 옵션이 뭔지 확인
            val radioSelected = MyApplication.prefs.getInt(Util.SET_MAINSCREEN_NUM, R.id.radio_cur_spend)
            val displayValue = when(radioSelected){
                R.id.radio_cur_spend -> result.expenditure
                R.id.radio_rest_month -> result.income - result.expenditure
                else -> result.balance
            }
            // 해당 옵션에 따른 값 표시
            displayMainStatic.postValue("${Util.numberStringComma(displayValue.toString())}원")
            displayOption.postValue(MyApplication.prefs.getString(Util.SET_MAINSCREEN_NAME, Util.RADIO_MAINSCREEN[R.id.radio_cur_spend]!!))
        })
    }

    fun getEvents(date: String, oper: (List<EcoEvent>) -> Unit){
        ApiService.getEvents(userId, date, operSuccess = { result ->
            oper(result)
        })
    }
}