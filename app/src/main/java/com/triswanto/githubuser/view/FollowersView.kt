package com.triswanto.githubuser.view

interface FollowersView {
    interface GetFollowers {
        fun getFollowerList(username: String?)
    }
}
