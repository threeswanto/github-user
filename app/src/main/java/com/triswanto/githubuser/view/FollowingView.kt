package com.triswanto.githubuser.view

interface FollowingView {
    interface GetFollowing {
        fun getFollowingList(username: String?)
    }
}
