package com.gara.findgithubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gara.findgithubuserapp.R
import com.gara.findgithubuserapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private var user: List<User>) : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    class UserHolder(view: View) : RecyclerView.ViewHolder(view){
        private val ivProfileImage = view.profile_image
        private val tvProfileName = view.profile_name

        fun bindUser(user: User){
            Picasso.get()
                .load(user.profileImage)
                .resize(36,36)
                .centerCrop()
                .into(ivProfileImage)
            tvProfileName.text = user.profileName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindUser(user[position])
    }

    override fun getItemCount(): Int {
        return user.size
    }

    fun update(user : List<User>){
        this.user = user
        notifyDataSetChanged()
    }
}