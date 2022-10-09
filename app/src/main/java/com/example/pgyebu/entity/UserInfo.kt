package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserInfo(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("birthDate") var birthDate: String,
    @SerializedName("prevIncome") var prevIncome: Long,
    @SerializedName("prevExpenditure") var prevExpenditure: Long,
    @SerializedName("settingDto") var settingDto: SettingDto,
)

data class SettingDto(
    @SerializedName("initDay") var initDay: Long,
    @SerializedName("displayOption") var displayOption: String,
)
/*
* "id": "heo01",
        "name": "test",
        "email": "heo01@naver.com",
        "birthDate": null,
        "prevIncome": 0,
        "prevExpenditure": 0,
        "settingDto": {
            "initDay": 12,
            "displayOption": "TOTAL_BALANCE"
        }
* */
