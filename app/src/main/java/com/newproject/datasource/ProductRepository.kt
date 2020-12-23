package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.network.APIinterface
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun getAllProducts(): MutableLiveData<Product> {
        val data = MutableLiveData<Product>()
        val call = apiInterface!!.getAllProducts()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                data.value = Product(Constant.getFailureCode())
                Debug.e("getAllProducts isFailed", t.message)
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val allVisitsData = response.body()
                        allVisitsData!!.statusCode = httpStatus
                        data.value = allVisitsData
                        Debug.e("getAllProducts isSuccessful", Gson().toJson(allVisitsData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val allVisitsData = Gson().fromJson<Product>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<Product>() {}.type
                            )
                            allVisitsData.statusCode = httpStatus
                            data.value = allVisitsData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = Product(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = Product(httpStatus)
                }
            }
        })
        return data
    }
}