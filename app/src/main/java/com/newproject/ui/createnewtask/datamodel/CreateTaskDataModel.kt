package com.newproject.ui.createnewtask.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class CreateTaskDataModel {
    var company_id: String? = null
    var lead_person_id: String? = null
    var area_id: String? = null
    var employee_id: String? = null
    var visitDate: String? = null
    var visitTime: String? = null
    var city: String? = null

    //    var card: String? = null
    var card: File? = null
    var visit_topic: String? = null

    fun createTask(context: Context): MutableLiveData<CreateTask> {
        lateinit var requestBody: MultipartBody
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//        builder.addFormDataPart("name", name!!)
//            .addFormDataPart("email", email!!)

        if (company_id != null) {
            try {
                builder.addFormDataPart("company_id", company_id!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (lead_person_id != null) {
            try {
                builder.addFormDataPart("lead_person_id", lead_person_id!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (area_id != null) {
            try {
                builder.addFormDataPart("area_id", area_id!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (employee_id != null) {
            try {
                builder.addFormDataPart("employee_id", employee_id!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (visitDate != null) {
            try {
                builder.addFormDataPart("visitDate", visitDate!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (visitTime != null) {
            try {
                builder.addFormDataPart("visitTime", visitTime!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (city != null) {
            try {
                builder.addFormDataPart("city", city!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (visit_topic != null) {
            try {
                builder.addFormDataPart("visit_topic", visit_topic!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (card != null) {
            try {
                builder.addFormDataPart(
                    "card", card!!.name,
                    RequestBody.create(MediaType.parse("multipart/form-data"), card!!)
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        requestBody = builder.build()
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val taskRepository = TaskRepository(apInterface)
        return taskRepository.createTask(requestBody)
    }
}