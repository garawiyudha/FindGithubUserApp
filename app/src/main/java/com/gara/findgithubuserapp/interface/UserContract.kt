package com.gara.findgithubuserapp.`interface`

import com.gara.findgithubuserapp.model.User
import retrofit2.Retrofit

interface UserContract {
    interface UserView {
        fun setUserAdapterList(user: List<User>, page: Int)

        fun showErrorMessage(message: String?)
    }

    interface UserPresenter{
        fun initRetrofit() : Retrofit

        fun getUserData(perPage : Int = 0, page : Int = 1)

        fun getNextPageData(perPage: Int, page: Int)
    }
}