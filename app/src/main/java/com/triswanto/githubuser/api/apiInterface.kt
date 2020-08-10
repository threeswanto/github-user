package com.triswanto.githubuser.api

import com.triswanto.githubuser.model.ListUser
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.model.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun getUsers(@Query("q") keyword: String?): Call<ListUser>

    @GET("users/{user}/followers")
    fun getFollowers(@Path("user") user: String?): Call<List<User>>

    @GET("users/{user}/following")
    fun getFollowing(@Path("user") user: String?): Call<List<User>>

    @GET("users/{user}")
    fun getDetailUser(@Path("user") user: String?): Call<UserDetail>
}
