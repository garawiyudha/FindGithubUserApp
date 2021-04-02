package com.gara.findgithubuserapp.presenter

import android.content.Context
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.model.UserResponse
import com.gara.findgithubuserapp.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserPresenterImpl(context : Context, private val mView : UserContract.UserView) : UserContract.UserPresenter {
    private val baseUrl = "https://api.github.com/"

    override fun getUserData(username: String) {
        val retrofit = initRetrofit()
        val service = retrofit.create(UserService::class.java)
        val progressDialog = mView.showProgressDialog()

        try {
            if (!username.isNullOrEmpty()) {
                service.searchUsers(10, username, page = 1).enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.totalCount!! > 0){
                            mView.setUserAdapterList(response.body()?.user!!)
//                            mView.hideProgressDialog(progressDialog)
                        } else {
                            val message = if (!response.isSuccessful)
                                "Failed to retrieve data" else "Data not found"
                            mView.showErrorMessage(message)
//                            mView.hideProgressDialog(progressDialog)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        mView.showErrorMessage(t.message)
//                        mView.hideProgressDialog(progressDialog)
                    }

                })

            } else {
                mView.showErrorMessage("Username cannot be empty !")
//                mView.hideProgressDialog(progressDialog)
            }
        } catch (e: Exception){
            mView.showErrorMessage(e.message)
//            mView.hideProgressDialog(progressDialog)
        } finally {
            mView.hideProgressDialog(progressDialog)
        }

    }

    override fun initRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}