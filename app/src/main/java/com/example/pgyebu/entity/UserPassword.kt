package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName

data class UserPassword(
    @SerializedName("originPassword") val originPassword: String,
    @SerializedName("newPassword") val newPassword: String
)
