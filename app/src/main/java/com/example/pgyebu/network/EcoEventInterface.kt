package com.example.pgyebu.network

import com.example.pgyebu.entity.*
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDateTime

interface EcoEventInterface {

    @GET("api/ecoevents/")
    fun getEcoEvent(): Call<EcoEventList>

    @GET("api/ecoevents/condition")
    fun getEcoEventDay(
        @Query("userId") userId: String,
        @Query("startDate") startDate: String,
    ): Call<EcoEventList>

    @GET("api/categories/list")
    fun getCategoryList(
        @Query("useYn") useYn: String
    ): Call<TypeListResponse<EventCategory>>

    @GET("api/ecoevents/display")
    fun getDisplayMainStatic(
        @Query("userId") userId: String
    ): Call<TypeResponse<DisplayMainStatic>>

    //@FormUrlEncoded
    @POST("login")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<BasicResponse>

    @POST("logout")
    fun logout(): Call<BasicResponse>

    @POST("api/users")
    fun signUp(
        @Body params: HashMap<String, String>
    ): Call<AnyResponse>

    @PUT("api/users/{userId}")
    fun updateUserInfo(
        @Path("userId") userId: String,
        @Body params: HashMap<String, Any>
    ): Call<TypeResponse<UserInfo>>

    @DELETE("api/users/{userId}")
    fun deleteUser(
        @Path("userId") userId: String,
    ): Call<AnyResponse>

    @POST("api/ecoevents/")
    fun registerEvent(
        @Body params: HashMap<String, String>
    ): Call<BasicResponse>

    @PATCH("api/ecoevents/{seq}")
    fun patchEvent(
        @Path("seq") seq: Long,
        @Body params: HashMap<String, String>
    ): Call<AnyResponse>

    @GET("api/ecoevents/summary")
    fun getEventStatic(
        @Query("userId") userId: String,
        @Query("startDate") startDate: String,
    ): Call<TypeResponse<EventStatic>>

    @PATCH("api/users/setting/{userId}")
    fun patchUserSetting(
        @Path("userId") userId: String,
        @Body params: HashMap<String, Any>
    ): Call<TypeResponse<UserSetting>>

    @PATCH("api/users/password/{userId}")
    fun patchUserPassword(
        @Path("userId") userId: String,
        @Body params: HashMap<String, String>
    ): Call<TypeResponse<Boolean>>

    @POST("api/users/password/{userId}")
    fun getPwSearch(
        @Path("userId") userId: String,
        @Body params: HashMap<String, Any>
    ): Call<BasicResponse>
}