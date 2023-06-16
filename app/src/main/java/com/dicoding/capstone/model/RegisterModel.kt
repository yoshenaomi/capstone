package com.dicoding.capstone.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

data class RegisBody(
    @SerializedName("username") val username : String,
    @SerializedName("email") val email :String,
    @SerializedName("password") val password :String
)

data class Dashboard(
    @SerializedName("username") val username: String
)
