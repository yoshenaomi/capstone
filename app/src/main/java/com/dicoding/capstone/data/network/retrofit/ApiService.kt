package com.dicoding.capstone.data.network.retrofit

import com.dicoding.capstone.model.LoginBody
import com.dicoding.capstone.model.LoginModel
import com.dicoding.capstone.model.NewsModel
import com.dicoding.capstone.model.ProfileModel
import com.dicoding.capstone.model.RegisBody
import com.dicoding.capstone.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST("login")
    fun login(
        @Body loginBody: LoginBody
    ): Call<LoginModel>

    @Headers("Content-Type:application/json")
    @POST("register")
    fun regis(
        @Body registerBody: RegisBody
    ): Call<RegisterModel>

    @GET("news")
    fun getNews(): Call<List<NewsModel>>

    @GET("profil")
    fun getDetailProfile(
        @Header("Authorization") Authorization: String
    ): Call<ProfileModel>
}
