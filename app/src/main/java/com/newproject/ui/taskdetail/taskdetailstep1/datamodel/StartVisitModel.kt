package com.newproject.ui.taskdetail.taskdetailstep1.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import java.io.IOException

class StartVisitModel {

    var visitId: String? = null
    var latitude: String? = null
    var longitude: String? = null

    fun startVisit(context: Context): MutableLiveData<StartVisit> {
        val data: MutableMap<String, String> = HashMap()
        if (visitId != null) {
            try {
                data["visit_id"] = visitId!!

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (latitude != null) {
            try {
                data["lat"] = latitude!!

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (longitude != null) {
            try {
                data["long"] = longitude!!

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val taskRepository = TaskRepository(apInterface)
        return taskRepository.startVisit(data)
    }

}