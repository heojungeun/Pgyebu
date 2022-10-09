package com.example.pgyebu.network

import android.os.Build
import com.example.pgyebu.Utils.Util
import com.example.pgyebu.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class ApiService {
    companion object {
        private val TAG = "ApiService"
        private val call = EcoEventObject.getRetrofitService

        fun logout(operSuccess: () -> Unit) {
            call.logout().enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    operSuccess()
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun getDisplayMainStatics(id: String, operSuccess: (DisplayMainStatic) -> Unit) {
            call.getDisplayMainStatic(userId = id).enqueue(object:
                Callback<TypeResponse<DisplayMainStatic>> {
                override fun onResponse(
                    call: Call<TypeResponse<DisplayMainStatic>>,
                    response: Response<TypeResponse<DisplayMainStatic>>
                ) {
                    if(response.body()!!.status == Util.STATUSOK){
                        val result = response.body()!!.data
                        operSuccess(result)
                    } else {
                        println("$TAG: - get Static error: \n${response.body()!!.message}")
                    }

                }

                override fun onFailure(call: Call<TypeResponse<DisplayMainStatic>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun getStaticsData(id: String, today: String, operSuccess: (EventStatic) -> Unit) {
            call.getEventStatic(userId = id, startDate = today).enqueue(object:
                Callback<TypeResponse<EventStatic>> {
                override fun onResponse(
                    call: Call<TypeResponse<EventStatic>>,
                    response: Response<TypeResponse<EventStatic>>
                ) {
                    if(response.body()!!.status == Util.STATUSOK){
                        val result = response.body()!!.data
                        operSuccess(result)
                    } else {
                        println("$TAG: - get Static error: \n${response.body()!!.message}")
                    }

                }

                override fun onFailure(call: Call<TypeResponse<EventStatic>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun getEvents(userId: String, date: String, operSuccess: (List<EcoEvent>) -> Unit){
            call.getEcoEventDay(
                userId = userId,
                startDate = date
            ).enqueue(object: Callback<EcoEventList> {
                override fun onResponse(call: Call<EcoEventList>, response: Response<EcoEventList>) {
                    if (response.body()?.status == "OK"){
                        operSuccess(response.body()!!.data)
                        println("$TAG: Get EcoEvents completed!")
                    }
                }

                override fun onFailure(call: Call<EcoEventList>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun getCategoryList(operSuccess: (List<EventCategory>) -> Unit){
            call.getCategoryList("Y").enqueue(object: Callback<TypeListResponse<EventCategory>>{
                override fun onResponse(
                    call: Call<TypeListResponse<EventCategory>>,
                    response: Response<TypeListResponse<EventCategory>>
                ) {
                    if (response.body()?.status == "OK") {
                        val result = response.body()!!.data
                        operSuccess(result)
                        println("$TAG: Get getCategoryList completed!")
                    }
                }

                override fun onFailure(call: Call<TypeListResponse<EventCategory>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun getPwSearch(userId: String, userEmail: String, operSuccess: (String) -> Unit){
            val body = HashMap<String, Any>()
            body["id"] = userId
            body["email"] = userEmail
            call.getPwSearch(userId, body).enqueue(object: Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.body()?.status == "OK") {
                        val result = response.body()!!.data
                        operSuccess(result)
                        println("$TAG: Get PassWord completed!")
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun updateUserInfo(userId: String, name: String="", email: String="", birthDate: String="", operSuccess: () -> Unit){
            val body = HashMap<String, Any>()
            if (name.isNotEmpty())
                body["name"] = name
            if (email.isNotEmpty())
                body["email"] = email
            if (birthDate.isNotEmpty())
                body["birthDate"] = birthDate
            call.updateUserInfo(userId, body).enqueue(object: Callback<TypeResponse<UserInfo>>{
                override fun onResponse(
                    call: Call<TypeResponse<UserInfo>>,
                    response: Response<TypeResponse<UserInfo>>
                ) {
                    if (response.body()?.status == "OK") {
                        //val result = response.body()!!.data
                        operSuccess()
                        println("$TAG: Update User info completed!")
                    }
                }

                override fun onFailure(call: Call<TypeResponse<UserInfo>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun updateUserPassword(userId: String, origin: String, new: String, operSuccess: () -> Unit, operFailure: () -> Unit){
            val body = HashMap<String, String>()
            body["originPassword"] = origin
            body["newPassword"] = new
            call.patchUserPassword(userId, body).enqueue(object: Callback<TypeResponse<Boolean>>{
                override fun onResponse(
                    call: Call<TypeResponse<Boolean>>,
                    response: Response<TypeResponse<Boolean>>
                ) {
                    if (response.body()?.status == "OK" && response.body()?.data == true) {
                        operSuccess()
                        println("$TAG: Update User info completed!")
                    }
                }

                override fun onFailure(call: Call<TypeResponse<Boolean>>, t: Throwable) {
                    t.printStackTrace()
                    operFailure()
                }
            })
        }

        fun deleteUser(userId: String, operSuccess: () -> Unit){
            call.deleteUser(userId).enqueue(object: Callback<AnyResponse>{
                override fun onResponse(call: Call<AnyResponse>, response: Response<AnyResponse>) {
                    if (response.body()?.status == "OK") {
                        //val result = response.body()!!.data
                        operSuccess()
                        println("$TAG: delete User completed!")
                    } else {
                        println("$TAG: delete User 실패!\ndata: ${response.body()?.data}\nmessage: ${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<AnyResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun setUserSetting(userId: String, initDay: Int = 0, displayOption: String = ""){
            val body = HashMap<String, Any>()
            if(initDay != 0)
                body["initDay"] = initDay
            if (displayOption != "")
                body["displayOption"] = displayOption
            call.patchUserSetting(userId, body).enqueue(object: Callback<TypeResponse<UserSetting>>{
                override fun onResponse(
                    call: Call<TypeResponse<UserSetting>>,
                    response: Response<TypeResponse<UserSetting>>
                ) {
                    if (response.body()?.status == "OK") {
                        println("$TAG: set Setting completed!")
                    }
                }

                override fun onFailure(call: Call<TypeResponse<UserSetting>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}