package com.triswanto.githubuser.presenter

import com.triswanto.githubuser.FollowersFragment
import com.triswanto.githubuser.api.ApiClient
import com.triswanto.githubuser.api.ApiInterface
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.view.FollowersView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterFollowers(private val initView: FollowersFragment) : FollowersView.GetFollowers {

    override fun getFollowerList(username: String?) {
        initView.showLoading()
        val apiInterface: ApiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!
        val call: Call<List<User>> = apiInterface.getFollowers(username)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                initView.hideLoading()
                if (response.body() is List<User>) {
                    initView.userList(response.body())
                } else {
                    initView.userListFailure("Api Limited", "Please Use Token")
                }
                if (!response.isSuccessful) {
                    initView.userListFailure(
                        "No Result for '$username'",
                        "Try Searching for Other Users"
                    )
                }
            }

            override fun onFailure(
                call: Call<List<User>>,
                t: Throwable
            ) {
                initView.userListFailure("Error Loading For '$username'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }
}