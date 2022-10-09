package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Req_EcoEventCondition(
    @SerializedName("userId") val userId: String,
    @SerializedName("eventType") var eventType: String? = null,
    @SerializedName("startDate") val startDate: String? = null,
    @SerializedName("endDate") val endDate: String? = null,
)

//        {
//            "userId": "user2",
//            "eventType" : "EXPENDITURE",
//            "startDate" : "2022-05-07T01:39:41.546492",
//            "endDate" : "2022-05-08T01:39:41.546492"
//        }