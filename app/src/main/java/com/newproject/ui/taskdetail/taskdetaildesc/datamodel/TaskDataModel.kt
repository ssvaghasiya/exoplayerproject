package com.newproject.ui.taskdetail.taskdetaildesc.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import java.io.IOException

class TaskDataModel {

    var visitId: String? = null

    fun getTask(context: Context): MutableLiveData<TaskDetails> {
        val data: MutableMap<String, String> = HashMap()
        if (visitId != null) {
            try {
                data["visit_id"] = visitId!!

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val taskRepository = TaskRepository(apInterface)
        return taskRepository.getTask(data)
    }

}