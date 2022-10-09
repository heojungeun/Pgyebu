package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class EventCategory(
    @SerializedName("seq") val seq: Long,
    @SerializedName("name") var name: String,
    @SerializedName("eventType") val eventType: String,
    @SerializedName("defaultPrice") var amount: Long,
    @SerializedName("useYn") var useYn: String
)

