package com.triswanto.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.triswanto.githubuser.adapter.ListFragmentAdapter
import com.triswanto.githubuser.model.User
import com.triswanto.githubuser.presenter.PresenterFollowings
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowingFragment(username: String) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var recyclerViewFollowings: RecyclerView? = null
    private var presenterFollowing: PresenterFollowings? = null
    private var followings: List<User>? = null
    private var usernameHere = username
    private var emptyView: RelativeLayout? = null
    private var errorTitle: TextView? = null
    private var errorMessage: TextView? = null
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            presenterFollowing!!.getFollowingList(usernameHere)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewFollowings = view.findViewById(R.id.recycle_view_following)
        emptyView = view.findViewById(R.id.empty_view)
        errorTitle = view.findViewById(R.id.errorTitle)
        errorMessage = view.findViewById(R.id.errorMessage)
        progressBar = view.findViewById(R.id.progressFollowing)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerViewFollowings?.layoutManager = layoutManager
        recyclerViewFollowings?.setHasFixedSize(true)
        presenterFollowing = PresenterFollowings(this)
        presenterFollowing!!.getFollowingList(usernameHere)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }


    fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
        recyclerViewFollowings!!.visibility = View.INVISIBLE
        errorView(View.INVISIBLE, "", "")
    }

    fun hideLoading() {
        progressBar!!.visibility = View.INVISIBLE
        recyclerViewFollowings!!.visibility = View.VISIBLE
    }

    fun userList(items: List<User>?) {
        followings = items
        val adapter = ListFragmentAdapter(followings as ArrayList<User>)
        recyclerViewFollowings?.adapter = adapter
        adapter.setOnItemClickCallback(object : ListFragmentAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
            }
        })
        adapter.notifyDataSetChanged()
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
}