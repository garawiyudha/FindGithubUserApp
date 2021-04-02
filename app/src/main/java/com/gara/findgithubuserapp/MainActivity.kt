package com.gara.findgithubuserapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.adapter.UserAdapter
import com.gara.findgithubuserapp.model.User
import com.gara.findgithubuserapp.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*


class MainActivity : AppCompatActivity(), UserContract.UserView  {
    val userAdapter = UserAdapter(arrayListOf<User>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mPresenter = UserPresenterImpl(this, this)

        bt_search.setOnClickListener {
            val name = et_name.text.toString()

            mPresenter.getUserData(name)
        }

        rv_user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

    }

    override fun showErrorMessage(message :String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun setUserAdapterList(user: List<User>) {
        userAdapter.update(user)
    }
}