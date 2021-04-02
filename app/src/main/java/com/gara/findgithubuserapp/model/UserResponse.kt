package com.gara.findgithubuserapp.model

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("total_count") val totalCount: Int? = 0,
    @SerializedName("incomplete_results") val incompleteResult: Boolean? = false,
    @SerializedName("items") val user: List<User>? = null

    )

data class User (
    @SerializedName("login") val profileName: String? = null,
    @SerializedName("avatar_url") val profileImage: String? = null,
    @SerializedName("url") val profileUrl: String? = null
)
