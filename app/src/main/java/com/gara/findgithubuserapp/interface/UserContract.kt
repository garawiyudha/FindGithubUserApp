package com.gara.findgithubuserapp.`interface`

import com.gara.findgithubuserapp.model.User

interface UserContract {
    interface UserView {
        fun setUserAdapterList(user: List<User>)

        fun showErrorMessage(message: String?)
    }

    interface UserPresenter{
        fun getUserData(username : String?)
    }
}