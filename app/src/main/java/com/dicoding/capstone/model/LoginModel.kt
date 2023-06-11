package com.dicoding.capstone.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    val result: Result,
    val error: Boolean,
    val message: String,
    val status: Int
)

data class Result(
    val userID: String,
    val email: String,
    val token: String,
    val username: String
)

data class LoginBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
