package com.example.pgyebu.view.chart

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pgyebu.MyApplication
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.entity.EventStatic
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChartViewModel {
    private val TAG = "ChartViewModel"
    private val _startAddActivityEvent = SingleLiveEvent<Any>()
    val startSignUpActivityEvent: LiveData<Any>
        get() = _startAddActivityEvent

    var whoWin = MutableLiveData<String>()
    var thisMonthExpenditureInfos = MutableLiveData<String>()
    var prevMinusCurSpend = MutableLiveData<String>()
    var scaleWordLiveData = MutableLiveData<String>()
    var categoryMostUsed = MutableLiveData<String>()
    init {
        categoryMostUsed.postValue("")
        prevMinusCurSpend.postValue("")
    }

    fun onAddButtonClick() {
        _startAddActivityEvent.call()
    }

    fun getChartData(){

    }

    // 숫자 콤마 추가
    fun getStatic(operSuccess: (EventStatic) -> Unit){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime: LocalDateTime = LocalDateTime.now()
            val formatted = localDateTime.format(DateTimeFormatter.ISO_DATE)
            val today_isodate = formatted.toString()

            val id = MyApplication.prefs.getString(Util.IS_LOGIN, Util.NOLOGIN)

            ApiService.getStaticsData(id, today_isodate, operSuccess = { result ->
                // moMExpenditureInfos[0]: 저번 달 지출 - 이번달 지출
                var pmcs = result.moMExpenditureInfos[0]
                var scaleWord = "적게 썼어요"
                if(pmcs.contains("-")){
                    // 이번달 지출 > 저번 달 지출
                    scaleWord = "많이 썼어요"
                    pmcs = pmcs.substring(1)
                }
                prevMinusCurSpend.postValue("${Util.numberStringComma(pmcs)}원")
                scaleWordLiveData.postValue(scaleWord)

                // thisMonthExpenditureInfos[0]: 이번 달 수입 대비 지출
                thisMonthExpenditureInfos.postValue("${Util.numberStringComma(result.thisMonthExpenditureInfos[0])}원")

                // categoryDtoList[0]: 이번 달 가장 많이 지출한 카테고리
                categoryMostUsed.postValue(result.categoryDtoList[0].name)

                // thisMonthExpenditureInfoPercent: 이번 달 수입, 지출 퍼센트
                val spendPer = result.thisMonthExpenditureInfoPercent[0].toFloat()
                val incomePer = result.thisMonthExpenditureInfoPercent[1].toFloat()
                if(incomePer < spendPer) {
                    whoWin.postValue("지출이 수입을 이기고 있어요!")
                } else if(incomePer == spendPer) {
                    whoWin.postValue("수입과 지출이 균형잡혀 있어요!")
                } else {
                    whoWin.postValue("수입이 지출을 이기고 있어요!")
                }

                operSuccess(result)
            })
        }
    }

}