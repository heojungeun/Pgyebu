package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName

data class UserSetting(
    @SerializedName("initDay") val initDay: Int,
    @SerializedName("displayOption") var displayOption: String
)

/*
* {
        "initDay": 24,
        "displayOption": "MONTH_BALANCE"
    },
*/