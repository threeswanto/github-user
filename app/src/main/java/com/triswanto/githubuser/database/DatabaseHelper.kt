package com.triswanto.githubuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.AVATAR
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.HTML
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.ID
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.TABLE_USER
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.USERNAME

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "databaseGithub"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_USER" +
                "($ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$USERNAME TEXT NOT NULL, " +
                "$AVATAR TEXT NOT NULL," +
                "$HTML TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

}