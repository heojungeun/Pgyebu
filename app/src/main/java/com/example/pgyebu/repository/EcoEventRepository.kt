package com.example.pgyebu.repository

import android.app.Application
import com.example.pgyebu.entity.EcoEvent
import com.example.pgyebu.entity.EcoEventList
import com.example.pgyebu.network.EcoEventObject
import com.google.gson.JsonObject
import retrofit2.Response

class EcoEventRepository(application: Application) {

    // Use Retrofit
//    suspend fun retrofitSelectAllTodo(): EcoEventList {
//        val response = EcoEventObject.getRetrofitService.getEcoEvent()
//        return if (response.isExecuted) response. as EcoEventList
//            else EcoEventList(ArrayList())
//    }

//    suspend fun retrofitInsertTodo(ecoEvent: EcoEvent) : Response<JsonObject> {
//        return EcoEventObject.getRetrofitService
//            .postEvent(ecoEvent.id, ecoEvent.amount, ecoEvent.eventType, ecoEvent.category)
//    }

    // singleton pattern
    companion object {
        private var instance: EcoEventRepository? = null

        fun getInstance(application: Application): EcoEventRepository? {
            if (instance == null)
                instance = EcoEventRepository((application))
            return instance
        }
    }

}