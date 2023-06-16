package com.dicoding.capstone.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("images")
	val images: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
