package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName

data class EcoEventList(
    @SerializedName("data") val data: List<EcoEvent>,
//    = listOf(
//        EcoEvent(
//            id = 999, userId = "-1", eventType = "EXPENDITURE",
//            useDate= "2022-07-03 01:39:41", amount = 90000, content = "yummy yum",
//            categorySeq = 2, category = "카페")
//    ),
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class EcoEventListTest(
    @SerializedName("data") val data: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)