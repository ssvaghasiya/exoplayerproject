package com.newproject.base.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.UserRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class UserDataModel {
    fun getArea(context: Context): MutableLiveData<AllArea> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val userRepository = UserRepository(apInterface)
        return userRepository.getArea("")
    }
}