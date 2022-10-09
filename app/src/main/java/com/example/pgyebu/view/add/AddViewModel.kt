package com.example.pgyebu.view.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.entity.AnyResponse
import com.example.pgyebu.entity.BasicResponse
import com.example.pgyebu.entity.EventCategory
import com.example.pgyebu.event.SingleLiveEvent
import com.example.pgyebu.network.ApiService
import com.example.pgyebu.network.EcoEventObject
import okhttp3.internal.cacheGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class AddViewModel {
    private val TAG = "AddViewModel"
    private val _startSaveActivityEvent = SingleLiveEvent<Any>()
    val startSaveActivityEvent: LiveData<Any>
        get() = _startSaveActivityEvent

//    private val _startChartActivityEvent = SingleLiveEvent<Any>()
//    val startLogInActivityEvent: LiveData<Any>
//        get() = _startChartActivityEvent

    fun onSaveButtonClick() {
        _startSaveActivityEvent.call()
    }

    /*
   * "userId" : "member1",
    "eventType" : "INCOME",
    "useDate" : "2022-07-15T01:39:41.546492",
    "amount" : "10000000",
    "desc" : "월급",
    "categorySeq" : "1"
   * */

    fun getCategoryList(operSuccess: (List<EventCategory>) -> Unit){
        if(Util.categoryList.isNullOrEmpty()) {
            ApiService.getCategoryList { categories ->
                Util.categoryList = categories
                operSuccess(categories)
            }
        }else{
            operSuccess(Util.categoryList)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveEvent(
        userId: String,
        eventType: String,
        useDate: String,
        amount: String,
        desc: String,
        categorySeq: String,
        operationSuccess: () -> Unit,
        operationFailure: () -> Unit
    ){
        val call = EcoEventObject.getRetrofitService
        val body = HashMap<String, String>()
        body["userId"] = userId
        body["eventType"] = eventType
        body["useDate"] = useDate
        body["amount"] = amount
        body["desc"] = desc
        body["categorySeq"] = categorySeq
        call.registerEvent(body).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.body()!!.message != "SUCCESS"){
                    println("$TAG: 이벤트 저장 실패")
                    operationFailure()
                }else{
                    println("$TAG: 이벤트 저장 성공")
                    operationSuccess()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                println("$TAG: 이벤트 저장 실패")
                t.printStackTrace()
                operationFailure()
            }
        })
    }

    fun editEvent(
        seq: Long,
        eventType: String,
        amount: String,
        desc: String,
        categorySeq: String,
        operationSuccess: () -> Unit,
        operationFailure: () -> Unit
    ){
        val call = EcoEventObject.getRetrofitService
        val body = HashMap<String, String>()
        body["eventType"] = eventType
        body["amount"] = amount
        body["desc"] = desc
        body["categorySeq"] = categorySeq
        call.patchEvent(seq, body).enqueue(object: Callback<AnyResponse> {
            override fun onResponse(call: Call<AnyResponse>, response: Response<AnyResponse>) {
                if(response.body()!!.message != "SUCCESS"){
                    println("$TAG: 이벤트 저장 실패")
                    operationFailure()
                }else{
                    println("$TAG: 이벤트 저장 성공")
                    operationSuccess()
                }
            }

            override fun onFailure(call: Call<AnyResponse>, t: Throwable) {
                println("$TAG: 이벤트 저장 실패")
                t.printStackTrace()
                operationFailure()
            }
        })
    }
}