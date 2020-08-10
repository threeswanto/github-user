package com.triswanto.githubuser

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.triswanto.githubuser.adapter.SectionsPagerAdapter
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.model.UserDetail
import com.triswanto.githubuser.presenter.PresenterDetailUser
import kotlinx.android.synthetic.main.activity_detail_user.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailUser : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var presenterDetailUser: PresenterDetailUser? = null
    private var emptyView: RelativeLayout? = null
    private var errorTitle: TextView? = null
    private var errorMessage: TextView? = null
    private var users: UserDetail? = null

    companion object {
        var USER_ITEM = "user_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail_user_title)
        progressBar = findViewById(R.id.progressDetail)
        emptyView = findViewById(R.id.empty_view)
        errorTitle = findViewById(R.id.errorTitle)
        errorMessage = findViewById(R.id.errorMessage)
        val userItem: User = intent.getParcelableExtra(USER_ITEM)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                userItem.login,
                supportFragmentManager
            )
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        presenterDetailUser = PresenterDetailUser(this)
        presenterDetailUser!!.getDetailUser(userItem.login)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
        emptyView!!.visibility = View.GONE
        errorView(View.INVISIBLE, "", "")
    }

    fun hideLoading() {
        progressBar!!.visibility = View.GONE
        emptyView!!.visibility = View.GONE
    }

    fun userListFailure(errorMessage: String, keyword: String) {
        errorView(View.VISIBLE, errorMessage, keyword)
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

    fun userDetail(user: UserDetail) {
        users = user
        name_detail.text = user.login
        username_detail.text = user.name
        company_detail.text = user.company
        location_detail.text = user.location
        repository_detail.text = user.public_repos.toString()
        followers_detail.text = user.followers.toString()
        following_detail.text = user.following.toString()
        Glide.with(this).load(user.avatar_url).override(350, 550).into(avatar_detail)
    }

}