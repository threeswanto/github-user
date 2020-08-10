package com.triswanto.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.triswanto.githubuser.R
import com.triswanto.githubuser.model.User
import java.util.*


class ListFragmentAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follow, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListFragmentAdapter.ViewHolder, position: Int) {
        val userData = listUser[position]

        Glide.with(holder.itemView.context)
            .load(userData.avatar_url)
            .apply(RequestOptions().override(55, 55))
            .into(holder.avatarUser)

        holder.nameUser.text = userData.login
        holder.companyUser.text = userData.type

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatarUser: ImageView = itemView.findViewById(R.id.avatar_user_list)
        var nameUser: TextView = itemView.findViewById(R.id.name_user_list)
        var companyUser: TextView = itemView.findViewById(R.id.company_user_list)
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
