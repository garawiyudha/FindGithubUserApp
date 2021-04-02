package com.gara.findgithubuserapp.presenter

import android.content.Context
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.model.UserResponse
import com.gara.findgithubuserapp.services.UserService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserPresenterImpl(context: Context, private val mView: UserContract.UserView) : UserContract.UserPresenter {
    private val baseUrl = "https://api.github.com/"

    override fun getUserData(username: String) {
        val retrofit = initRetrofit()
        val service = retrofit.create(UserService::class.java)

        try {
            if (!username.isNullOrEmpty()) {
                service.searchUsers(10, username, page = 1).enqueue(object :
                    Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.totalCount!! > 0) {
                            mView.setUserAdapterList(response.body()?.user!!)
//                            mView.hideProgressDialog(progressDialog)
                        } else {
                            val message = if (!response.isSuccessful)
                                "Failed to retrieve data" else "Data not found"
                            mView.showErrorMessage(message)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        mView.showErrorMessage(t.message)
                    }

                })

            } else {
                mView.showErrorMessage("Username cannot be empty !")
            }
        } catch (e: Exception){
            mView.showErrorMessage(e.message)
        }

    }

    override fun initRetrofit() : Retrofit{
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}