package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ReEcoEvent(
    @SerializedName("userId") val userId: String,
    @SerializedName("eventType") var eventType: String,
    @SerializedName("useDate") val useDate: LocalDateTime,
    @SerializedName("amount") var amount: Long, // be 에서 Long이라서 변환해야함
    @SerializedName("desc") var content: String,
    @SerializedName("categoryName") var category: String
)


//{
//    "data": [
//    {
//        "seq": 2,
//        "userId": "user1",
//        "eventType": "EXPENDITURE",
//        "useDate": "2022-05-07 23:24:25",
//        "amount": 90000,
//        "desc": null,
//        "categorySeq": 3
//    }