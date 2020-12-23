package com.newproject.ui.login.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.UserRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class LoginDataModel {
    var email: String? = null
    var password: String? = null

    fun login(context: Context): MutableLiveData<LoginData> {
        val apInterface: APIinterface = APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val userRepository = UserRepository(apInterface)
        return userRepository.login(this)
    }
}