package com.gara.findgithubuserapp.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.gara.findgithubuserapp.R
import com.gara.findgithubuserapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(var user: List<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    class UserHolder(view: View) : RecyclerView.ViewHolder(view){
        private val ivProfileImage = view.profile_image
        private val tvProfileName = view.profile_name

        fun bindUser(user: User){
            Picasso.get()
                .load(user.profileImage)
                .resize(36, 36)
                .centerCrop()
                .into(ivProfileImage)
            tvProfileName.text = user.profileName
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val progressBar : ProgressBar = view.progressBar

        fun showProgressBar(){
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM)
            return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
        else
            return LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (user.get(position).profileName == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UserHolder)
            holder.bindUser(user[position])
        else if (holder is LoadingViewHolder)
            holder.showProgressBar()
    }

    override fun getItemCount(): Int {
        return user.size
    }

    fun update(user: List<User>){
        this.user = user
        notifyDataSetChanged()
    }
}