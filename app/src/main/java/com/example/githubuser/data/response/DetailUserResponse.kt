package com.example.githubuser.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailUserResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,
) : Serializable
