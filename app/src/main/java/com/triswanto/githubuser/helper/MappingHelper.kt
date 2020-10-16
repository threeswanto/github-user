package com.triswanto.githubuser.helper

import android.database.Cursor
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.AVATAR
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.HTML
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.ID
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.USERNAME
import com.triswanto.githubuser.model.UserLocal

object MappingHelper {

    fun mapCursorToList(cursor: Cursor?) : List<UserLocal>{
        val userList = ArrayList<UserLocal>()

        cursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val html = getString(getColumnIndexOrThrow(HTML))
                userList.add(UserLocal(username, avatar, html, id))
            }
        }

        return userList
    }
}