package com.triswanto.githubuser.view

interface MainView {
    interface GetUsers {
        fun getUserList(keyword: String?)
    }

}
