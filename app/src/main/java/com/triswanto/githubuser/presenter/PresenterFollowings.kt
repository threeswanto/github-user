package com.triswanto.githubuser.presenter

import com.triswanto.githubuser.FollowingFragment
import com.triswanto.githubuser.api.ApiClient
import com.triswanto.githubuser.api.ApiInterface
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.view.FollowingView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterFollowings(private val initView: FollowingFragment) : FollowingView.GetFollowing {

    override fun getFollowingList(username: String?) {
        initView.showLoading()
        val apiInterface: ApiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!
        val call: Call<List<User>> = apiInterface.getFollowing(username)
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