package com.example.pgyebu.entity

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("data") val data: String,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class AnyResponse(
    @SerializedName("data") val data: Any,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class TypeResponse<T: Any>(
    @SerializedName("data") val data: T,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class TypeListResponse<T: Any>(
    @SerializedName("data") val data: List<T>,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)