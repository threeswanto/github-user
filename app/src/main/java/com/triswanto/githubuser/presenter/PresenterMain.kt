package com.triswanto.githubuser.presenter

import com.triswanto.githubuser.MainActivity
import com.triswanto.githubuser.api.ApiClient
import com.triswanto.githubuser.api.ApiInterface
import com.triswanto.githubuser.model.ListUser
import com.triswanto.githubuser.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterMain(private val initView: MainActivity) : MainView.GetUsers {

    override fun getUserList(keyword: String?) {
        initView.showLoading()
        val apiInterface: ApiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)!!
        val call: Call<ListUser> = apiInterface.getUsers(keyword)
        call.enqueue(object : Callback<ListUser> {
            override fun onResponse(
                call: Call<ListUser>,
                response: Response<ListUser>
            ) {
                initView.hideLoading()
                initView.userList(response.body()!!.items)
                val totalCount: Int = response.body()!!.total_count
                if (!response.isSuccessful || totalCount == 0
                ) {
                    initView.userListFailure(
                        "No Result for '$keyword'",
                        "Try Searching for Other Users"
                    )
                }
            }

            override fun onFailure(
                call: Call<ListUser>,
                t: Throwable
            ) {
                initView.userListFailure("Error Loading For '$keyword'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }
}