package com.triswanto.githubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserLocal(

    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("html_url")
    val htmlUrl: String,
    @field:SerializedName("id")
    val id: Int


) : Parcelable