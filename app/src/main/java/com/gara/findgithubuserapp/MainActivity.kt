package com.gara.findgithubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.adapter.UserAdapter
import com.gara.findgithubuserapp.model.User
import com.gara.findgithubuserapp.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*


class MainActivity : AppCompatActivity(), UserContract.UserView  {
    private val userAdapter = UserAdapter(arrayListOf<User>())
    private val userLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    private var currentPage : Int = 1
    private val limitPerPage : Int = 15
    private var isNextPageEmpty = false
    private var isOnLoadingMore = false
    private val mPresenter = UserPresenterImpl(this, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_search.setOnClickListener {
            val name = et_name.text.toString()

            if (!name.isNullOrEmpty() && name != mPresenter.username){
                mPresenter.username = name

                currentPage = 1
                isNextPageEmpty = false

                mPresenter.getUserData(limitPerPage, currentPage)
            } else if (name.isEmpty()) {
                showErrorMessage(Constant.emptyUsername)
            }
        }

        rv_user.apply {
            layoutManager = userLayoutManager
            adapter = userAdapter
            addOnScrollListener(scrollListener)
        }

    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = userLayoutManager.childCount
            val totalItemCount = userLayoutManager.itemCount
            val firstVisibleItemPosition = userLayoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0 && totalItemCount >= 0 && totalItemCount >= limitPerPage
                    && !isNextPageEmpty){
                if (!isOnLoadingMore) {
                    isOnLoadingMore = true
                    loadMore()
                }
            }
        }
    }

    fun loadMore(){
        var currentList : MutableList<User> = userAdapter.user.toMutableList()
        val loading = User()
        currentList.add(User())
        userAdapter.user = currentList
        rv_user.post { userAdapter.notifyItemInserted(currentList.size - 1) }
        currentPage++
        Handler().postDelayed({

            currentList.remove(loading)
            userAdapter.user = currentList

            Thread {
                mPresenter.getNextPageData(limitPerPage, currentPage)
            }.start()
        }, 3000)

    }

    override fun showErrorMessage(message :String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun setUserAdapterList(user: List<User>, page: Int) {
        isOnLoadingMore = false

        if (page > 1) {
            if (user.isNotEmpty()) {
                var currentList = userAdapter.user.toMutableList()
                currentList.addAll(user)
                userAdapter.update(currentList)
            } else {
                isNextPageEmpty = true
            }
        } else {
            userAdapter.update(user)
        }
    }
}