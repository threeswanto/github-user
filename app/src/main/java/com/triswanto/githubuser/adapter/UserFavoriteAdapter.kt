package com.triswanto.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.triswanto.githubuser.DetailUser
import com.triswanto.githubuser.R
import com.triswanto.githubuser.model.UserLocal

class UserFavoriteAdapter(private val context: Context?, private val userList : List<UserLocal>)
    : RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>() {

    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view){

        private val imgUser = view.findViewById<ImageView>(R.id.card_view_avatar)
        private val tvUser = view.findViewById<TextView>(R.id.username_list)
        private val tvHtml = view.findViewById<TextView>(R.id.company_user_list)
        fun bind(user: UserLocal){
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(imgUser)
            tvUser.text = user.login
            tvHtml.text = user.htmlUrl
            tvHtml.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailUser::class.java)
            intent.putExtra(DetailUser.USER_ITEM, userList[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }
}