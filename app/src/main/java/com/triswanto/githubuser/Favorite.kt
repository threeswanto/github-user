package com.triswanto.githubuser

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.triswanto.githubuser.adapter.UserFavoriteAdapter
import com.triswanto.githubuser.database.UserContract.UserColumns.Companion.CONTENT_URI_USER
import com.triswanto.githubuser.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Favorite : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI_USER, true, myObserver)

        if (savedInstanceState == null){
            loadUserAsync()
        }

        rvFav.layoutManager = LinearLayoutManager(this)
    }


    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI_USER,null,null,null,null)
                MappingHelper.mapCursorToList(cursor)
            }

            val user = deferredUser.await()
            if (user.isNotEmpty()){
                rvFav.adapter = UserFavoriteAdapter(applicationContext, user)
            }
        }
    }

}