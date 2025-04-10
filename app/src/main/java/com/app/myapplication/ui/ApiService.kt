package com.app.myapplication.ui

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.google.gson.JsonElement

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun doLogin(
        @Field("phone_no") mobile: String
    ): Response<JsonElement>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun oottpp(
        @Field("phone_no") mobile: String,
        @Field("oottpp") oottpp: String
    ): Response<JsonElement>
}
