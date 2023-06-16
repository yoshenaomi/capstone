package com.dicoding.capstone.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
	@field:SerializedName("images")
	val images: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)