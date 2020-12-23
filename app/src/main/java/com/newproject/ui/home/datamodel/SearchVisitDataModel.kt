package com.newproject.ui.home.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import java.io.IOException

class SearchVisitDataModel {

    var search: String? = null
    var area_name: String? = null
    var date: String? = null

    fun getSearchVisit(context: Context): MutableLiveData<SearchVisitData> {

        val data: MutableMap<String, String> = HashMap()
        if (search != null) {
            try {
                data["search"] = search!!
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (area_name != null) {
            try {
                data["area_name"] = area_name!!
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (date != null) {
            try {
                data["date"] = date!!
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val targetRepository = TaskRepository(apInterface)
        return targetRepository.getSearchVisit(data)
    }
}