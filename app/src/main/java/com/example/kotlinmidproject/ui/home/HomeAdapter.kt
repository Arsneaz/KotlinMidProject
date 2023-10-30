package com.example.kotlinmidproject.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmidproject.R
import com.example.kotlinmidproject.model.User

class HomeAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val user = userList[position]
        holder.nameTextView.text = "${user.first_name} ${user.last_name}"
        holder.emailTextView.text = user.email

        // Load image using a library like Picasso or Glide
        // Example using Glide:
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.avatarImageView)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

}