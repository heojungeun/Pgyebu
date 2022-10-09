package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class EventStatic(
    @SerializedName("categoryDtoList") val categoryDtoList: Array<EventCategory>,
    @SerializedName("moMExpenditureInfos") var moMExpenditureInfos: Array<String>,
    @SerializedName("thisMonthExpenditureInfos") val thisMonthExpenditureInfos: Array<String>,
    @SerializedName("thisMonthExpenditureInfoPercent") var thisMonthExpenditureInfoPercent: Array<String>,
    @SerializedName("inTimeExpenseAmountMap") var inTimeExpenseAmountMap: HashMap<String, Int>,
)

/*
* {
    "data": {
        "categoryDtoList": [
            {
                "seq": 2,
                "name": "카페",
                "eventType": "EXPENDITURE",
                "defaultPrice": 0,
                "useYn": "Y"
            }
        ],
        "moMExpenditureInfos": [
            "-10161540",
            "0"
        ],
        "thisMonthExpenditureInfos": [
            "10161540",
            "0"
        ],
        "thisMonthExpenditureInfoPercent": [
            "100",
            "0"
        ],
        "inTimeExpenseAmountMap": {
            "18": 10000000,
            "19": 1000,
            "22": 156000,
            "23": 4540
        }
    },
    "status": "OK",
    "message": "SUCCESS"
}
*
* */