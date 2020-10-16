package com.triswanto.githubuser.database

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHOR_USER = "com.triswanto.githubuser"
    const val SCHEME_USER = "content"


    class UserColumns : BaseColumns {
        companion object{
            const val TABLE_USER = "user_table"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val HTML = "html"

            val CONTENT_URI_USER : Uri = Uri.Builder().scheme(SCHEME_USER)
                .authority(AUTHOR_USER)
                .appendPath(TABLE_USER)
                .build()


        }
    }
}