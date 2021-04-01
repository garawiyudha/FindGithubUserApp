package com.gara.findgithubuserapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gara.findgithubuserapp.adapter.UserAdapter
import com.gara.findgithubuserapp.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*


class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_search.setOnClickListener {
            var name = et_name.text

            Toast.makeText(applicationContext, name, Toast.LENGTH_SHORT).show()
        }

        val listUser = listOf(
            User(profileName = "octoberzhu", profileImage = "https://avatars.githubusercontent.com/u/1398235?v=4"),
            User(profileName = "octobrekids", profileImage = "https://avatars.githubusercontent.com/u/33366852?v=4"),
            User(profileName = "Octoberapples", profileImage = "https://avatars.githubusercontent.com/u/7982992?v=4")
        )

        val userAdapter = UserAdapter(listUser)

        rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

    }
}