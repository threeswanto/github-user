package com.triswanto.githubuser.presenter

import com.triswanto.githubuser.DetailUser
import com.triswanto.githubuser.api.ApiClient
import com.triswanto.githubuser.api.ApiInterface
import com.triswanto.githubuser.model.UserDetail
import com.triswanto.githubuser.view.DetailUserView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterDetailUser(private val initView: DetailUser) : DetailUserView.GetDetailUser {

    override fun getDetailUser(username: String?) {
        initView.showLoading()
        val apiInterface: ApiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!
        val call: Call<UserDetail> = apiInterface.getDetailUser(username)
        call.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                initView.hideLoading()
                if (response.body() is UserDetail) {
                    initView.userDetail(response.body()!!)
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
                call: Call<UserDetail>,
                t: Throwable
            ) {
                initView.userListFailure("Error Loading For '$username'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }

}