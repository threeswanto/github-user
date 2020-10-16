package com.triswanto.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.triswanto.githubuser.adapter.ListUserAdapter
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.presenter.PresenterMain
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var mainPresenterMain: PresenterMain? = null
    private var emptyView: RelativeLayout? = null
    private var errorTitle: TextView? = null
    private var errorMessage: TextView? = null
    private var users: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mainPresenterMain = PresenterMain(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_favorite) {
            intent = Intent(this, Favorite::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.btn_setting) {
            intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun init() {
        emptyView = findViewById(R.id.empty_view)
        errorTitle = findViewById(R.id.errorTitle)
        errorMessage = findViewById(R.id.errorMessage)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recycle_view_user)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = this.search

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainPresenterMain!!.getUserList(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
        recyclerView!!.visibility = View.INVISIBLE
        errorView(View.INVISIBLE, "", "")
    }

    fun hideLoading() {
        progressBar!!.visibility = View.INVISIBLE
        recyclerView!!.visibility = View.VISIBLE
    }

    fun userList(items: List<User>?) {
        users = items
        val adapter = ListUserAdapter(users as ArrayList<User>)
        recyclerView?.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedHero(data)
            }
        })
        adapter.notifyDataSetChanged()
    }

    fun userListFailure(errorMessage: String, keyword: String) {
        errorView(View.VISIBLE, errorMessage, keyword)
    }

    private fun showSelectedHero(user: User) {
        val mIntent = Intent(this, DetailUser::class.java)
        mIntent.putExtra(DetailUser.USER_ITEM, user)
        startActivity(mIntent)
    }

    private fun errorView(
        visibility: Int,
        title: String,
        message: String
    ) {
        emptyView!!.visibility = visibility
        errorTitle!!.text = title
        errorMessage?.text = message
    }
}