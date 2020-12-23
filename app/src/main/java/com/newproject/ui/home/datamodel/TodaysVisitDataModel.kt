package com.newproject.ui.home.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class TodaysVisitDataModel {

    fun getTodaysVisits(context: Context): MutableLiveData<TodaysVisitData> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val targetRepository = TaskRepository(apInterface)
        return targetRepository.getTodaysVisits()
    }
}