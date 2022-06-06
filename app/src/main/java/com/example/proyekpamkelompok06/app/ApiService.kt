package com.example.proyekpamkelompok06.app

import com.example.proyekpamkelompok06.model.ResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("namaLengkap") namaLengkap :String,
        @Field("noKTP") noKTP :String,
        @Field("noHP") noHP :String,
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<ResponseModel>

    @GET("item-menu-kantin/all")
    fun getProduk(): Call<ResponseModel>

    @GET("item-menu-pulsa/all")
    fun getPulsa(): Call<ResponseModel>
}