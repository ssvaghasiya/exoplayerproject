package com.newproject.ui.login.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.UserRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class LogoutDataModel {
    fun logOut(mContext: Context): MutableLiveData<LogoutData> {
        val apiInterface: APIinterface =
            APIClient.newRequestRetrofit(mContext).create(APIinterface::class.java)
        val outletRepository = UserRepository(apiInterface)
        return outletRepository.logOut()
    }
}