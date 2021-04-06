package com.gara.findgithubuserapp.presenter

import android.app.Activity
import com.gara.findgithubuserapp.Constant
import com.gara.findgithubuserapp.`interface`.UserContract
import com.gara.findgithubuserapp.model.User
import com.gara.findgithubuserapp.model.UserResponse
import com.gara.findgithubuserapp.services.UserService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserPresenterImpl(private val activity: Activity, private val mView: UserContract.UserView) : UserContract.UserPresenter {
    var username = ""

    override fun getUserData(perPage : Int, page : Int) {
        val service = createUserService()

        try {
            service.searchUsers(perPage, username, page = page).enqueue(object :
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful && response.body()?.totalCount!! > 0) {

                        mView.setUserAdapterList(response.body()?.user!!, page)
                    } else {
                        var message = ""
                        when (response.code()){
                            403 -> message = Constant.reachLimit
                            200 -> message = Constant.emptyResult
                            else -> Constant.failedResult
                        }

                        mView.showErrorMessage(message)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    mView.showErrorMessage(Constant.failedResult)
                }

            })
        } catch (e: Exception){
            mView.showErrorMessage(e.message)
        }

    }

    override fun getNextPageData(perPage: Int, page: Int){
        val service = createUserService()
        var result = emptyList<User>()
        try {
            val call = service.searchUsers(perPage, username, page = page).execute()
            if (call.isSuccessful){
                activity.runOnUiThread {
                    mView.setUserAdapterList(call.body()?.user!!, page)
                }
            } else {
                activity.runOnUiThread {
                    mView.showErrorMessage(Constant.failedResult)
                }
            }
        } catch (e: Exception){
            activity.runOnUiThread {
                mView.showErrorMessage(Constant.failedResult)
            }
        }
    }

    override fun initRetrofit() : Retrofit{
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createUserService() :UserService{
        val retrofit = initRetrofit()

        return retrofit.create(UserService::class.java)
    }
}