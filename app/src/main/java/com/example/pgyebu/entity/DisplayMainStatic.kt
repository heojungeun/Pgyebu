package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName

data class DisplayMainStatic(
    @SerializedName("income") val income: Long,
    @SerializedName("expenditure") val expenditure: Long,
    @SerializedName("balance") val balance: Long
)

/*
* "income": 1000000,
        "expenditure": 221340,
        "balance": 778660
* */