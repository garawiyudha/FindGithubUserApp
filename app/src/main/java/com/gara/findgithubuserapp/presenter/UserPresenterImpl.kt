package com.gara.findgithubuserapp.presenter

import android.content.Context
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.model.User

class UserPresenterImpl(context : Context, private val mView : UserContract.UserView) : UserContract.UserPresenter {
    override fun getUserData(username: String?) {
        val listUser = listOf(
            User(profileName = "octoberzhu", profileImage = "https://avatars.githubusercontent.com/u/1398235?v=4"),
            User(profileName = "octobrekids", profileImage = "https://avatars.githubusercontent.com/u/33366852?v=4"),
            User(profileName = "Octoberapples", profileImage = "https://avatars.githubusercontent.com/u/7982992?v=4")
        )

        if (!username.isNullOrEmpty()) mView.setUserAdapterList(listUser) else mView.showErrorMessage("Username cannot be empty !")
    }
}