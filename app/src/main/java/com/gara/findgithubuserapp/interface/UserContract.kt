package com.gara.findgithubuserapp.`interface`

import android.app.ProgressDialog
import com.gara.findgithubuserapp.model.User
import retrofit2.Retrofit

interface UserContract {
    interface UserView {
        fun setUserAdapterList(user: List<User>)

        fun showErrorMessage(message: String?)

        fun showProgressDialog() : ProgressDialog

        fun hideProgressDialog(progressDialog: ProgressDialog)
    }

    interface UserPresenter{
        fun initRetrofit() : Retrofit

        fun getUserData(username : String)
    }
}