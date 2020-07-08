package com.martha.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.martha.core_base.model.UserResponse
import com.martha.githubuser.R
import com.squareup.picasso.Picasso

class UserAdapter(val context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users: MutableList<UserResponse.User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindModel(users[position])
    }

    fun setUser(data: List<UserResponse.User>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    fun clearUser() {
        users.clear()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.tv_name)
        val photoImg: ImageView = itemView.findViewById(R.id.iv_image)

        fun bindModel(user: UserResponse.User) {
            nameTxt.text = user.login
            Picasso.get().load(user.avatarUrl).into(photoImg)
        }
    }
}