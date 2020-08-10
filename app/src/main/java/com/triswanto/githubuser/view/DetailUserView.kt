package com.triswanto.githubuser.view

interface DetailUserView {
    interface GetDetailUser {
        fun getDetailUser(username: String?)
    }
}