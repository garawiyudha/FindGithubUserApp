package com.gara.findgithubuserapp.services

import com.gara.findgithubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("search/users")
    fun searchUsers(@Query("per_page") perPage: Int, @Query("q") username: String,
                    @Query("order") order : String = "asc",@Query("page") page : Int) : Call<UserResponse>

}