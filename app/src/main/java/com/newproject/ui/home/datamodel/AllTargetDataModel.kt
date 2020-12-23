package com.newproject.ui.home.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TargetRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class AllTargetDataModel {

    fun getAllTargets(context: Context): MutableLiveData<AllTarget> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val targetRepository = TargetRepository(apInterface)
        return targetRepository.getAllTargets()
    }

}