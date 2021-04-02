package com.gara.findgithubuserapp.`interface`

import com.gara.findgithubuserapp.model.User
import retrofit2.Retrofit

interface UserContract {
    interface UserView {
        fun setUserAdapterList(user: List<User>)

        fun showErrorMessage(message: String?)
    }

    interface UserPresenter{
        fun initRetrofit() : Retrofit

        fun getUserData(username : String)
    }
}