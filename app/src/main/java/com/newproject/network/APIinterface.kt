package com.newproject.network

import com.newproject.base.datamodel.AllArea
import com.newproject.ui.login.datamodel.LoginData
import com.newproject.ui.login.datamodel.LoginDataModel
import com.newproject.ui.login.datamodel.LogoutData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIinterface {

    @POST("auth/login")
    fun login(@Body loginDataModel: LoginDataModel): Call<LoginData>

    @GET("auth/logout")
    fun logOut(): Call<LogoutData>


    @GET("listing/area/{id}")
    fun getArea(
        @Path(
            value = "id",
            encoded = true
        ) id: String
    ): Call<AllArea>

}