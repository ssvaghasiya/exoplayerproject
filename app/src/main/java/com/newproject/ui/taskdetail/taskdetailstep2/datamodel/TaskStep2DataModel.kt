package com.newproject.ui.taskdetail.taskdetailstep2.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.TaskRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import okhttp3.MultipartBody
import java.io.IOException

class TaskStep2DataModel {

    var visitId: String? = null
//    var sample: String? = null
//    var quantity: String? = null
    var doctor_response: String? = null
    var potential_pharmacy: String? = null
    var potential_product: String? = null
//    var order_product: String? = null
//    var order_quantity: String? = null
    var reason: String? = null
    var product_id: ArrayList<String> = arrayListOf()
    var product_quantity: ArrayList<String> = arrayListOf()
//    var sample_product_id: ArrayList<String> = arrayListOf()
//    var sample_product_quantity: ArrayList<String> = arrayListOf()

    fun updateTaskStep2(context: Context): MutableLiveData<TaskStep2Details> {
//        val mediaType: MediaType? = MediaType.parse("text/plain")
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        product_id.forEachIndexed { i, s ->
            builder.addFormDataPart("product_id[$i]", s)
        }
        product_quantity.forEachIndexed { i, s ->
            builder.addFormDataPart("product_quantity[$i]", s)
        }
//        sample_product_id.forEachIndexed { i, s ->
//            builder.addFormDataPart("sample_product_id[$i]", s)
//        }
//        sample_product_quantity.forEachIndexed { i, s ->
//            builder.addFormDataPart("sample_product_quantity[$i]", s)
//        }


//        if (visitId != null) {
//            try {
//                builder.addFormDataPart("visit_id", visitId)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }

//        if (sample != null) {
//            try {
//                builder.addFormDataPart("sample", sample)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        if (quantity != null) {
//            try {
//                builder.addFormDataPart("quantity", quantity)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
        if (doctor_response != null) {
            try {
                builder.addFormDataPart("doctor_response", doctor_response)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (potential_product != null) {
            try {
                builder.addFormDataPart("potential_product", potential_product)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (potential_pharmacy != null) {
            try {
                builder.addFormDataPart("potential_pharmacy", potential_pharmacy)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (reason != null) {
            try {
                builder.addFormDataPart("reason", reason)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
//        if (order_product != null) {
//            try {
//                builder.addFormDataPart("order_product", order_product)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        if (order_quantity != null) {
//            try {
//                builder.addFormDataPart("order_quantity", order_quantity)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }

        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val taskRepository = TaskRepository(apInterface)

        return taskRepository.updateTaskStep2(visitId!!, builder.build())
    }

}