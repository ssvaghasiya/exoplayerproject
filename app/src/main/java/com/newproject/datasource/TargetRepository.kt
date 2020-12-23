package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.network.APIinterface
import com.newproject.ui.home.datamodel.AllTarget
import com.newproject.ui.home.datamodel.MonthlyRevenueData
import com.newproject.ui.home.datamodel.MonthlyVisitsData
import com.newproject.ui.home.datamodel.SuccessfulMonthlyVisitsData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun getAllTargets(): MutableLiveData<AllTarget> {
        val data = MutableLiveData<AllTarget>()
        val call = apiInterface!!.getAllTargets()
        Debug.e("API", call.request().url().encodedPath())
//        Debug.e("body", Gson().toJson())
        call.enqueue(object : Callback<AllTarget> {
            override fun onFailure(call: Call<AllTarget>, t: Throwable) {
                data.value = AllTarget(Constant.getFailureCode())
                Debug.e("login isFailed", t.message)
            }

            override fun onResponse(call: Call<AllTarget>, response: Response<AllTarget>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val loginData = response.body()
                        loginData!!.statusCode = httpStatus
                        data.value = loginData
                        Debug.e("login isSuccessful", Gson().toJson(loginData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val loginData = Gson().fromJson<AllTarget>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<AllTarget>() {}.type
                            )
                            loginData.statusCode = httpStatus
                            data.value = loginData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = AllTarget(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = AllTarget(httpStatus)
                }
            }
        })
        return data
    }

    fun getSuccessfulMonthlyVisits(): MutableLiveData<SuccessfulMonthlyVisitsData> {
        val data = MutableLiveData<SuccessfulMonthlyVisitsData>()
        val call = apiInterface!!.getSuccessfulMonthlyVisits()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<SuccessfulMonthlyVisitsData> {
            override fun onFailure(call: Call<SuccessfulMonthlyVisitsData>, t: Throwable) {
                data.value = SuccessfulMonthlyVisitsData(Constant.getFailureCode())
                Debug.e("getSuccessfulMonthlyVisits isFailed", t.message)
            }

            override fun onResponse(call: Call<SuccessfulMonthlyVisitsData>, response: Response<SuccessfulMonthlyVisitsData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val successfulMonthlyVisitsData = response.body()
                        successfulMonthlyVisitsData!!.statusCode = httpStatus
                        data.value = successfulMonthlyVisitsData
                        Debug.e("getSuccessfulMonthlyVisits isSuccessful", Gson().toJson(successfulMonthlyVisitsData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val successfulMonthlyVisitsData = Gson().fromJson<SuccessfulMonthlyVisitsData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<SuccessfulMonthlyVisitsData>() {}.type
                            )
                            successfulMonthlyVisitsData.statusCode = httpStatus
                            data.value = successfulMonthlyVisitsData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = SuccessfulMonthlyVisitsData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = SuccessfulMonthlyVisitsData(httpStatus)
                }
            }
        })
        return data
    }

    fun getMonthlyVisits(): MutableLiveData<MonthlyVisitsData> {
        val data = MutableLiveData<MonthlyVisitsData>()
        val call = apiInterface!!.getMonthlyVisits()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<MonthlyVisitsData> {
            override fun onFailure(call: Call<MonthlyVisitsData>, t: Throwable) {
                data.value = MonthlyVisitsData(Constant.getFailureCode())
                Debug.e("MonthlyVisits isFailed", t.message)
            }

            override fun onResponse(call: Call<MonthlyVisitsData>, response: Response<MonthlyVisitsData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val monthlyVisitsData = response.body()
                        monthlyVisitsData!!.statusCode = httpStatus
                        data.value = monthlyVisitsData
                        Debug.e("MonthlyVisits isSuccessful", Gson().toJson(monthlyVisitsData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val monthlyVisitsData = Gson().fromJson<MonthlyVisitsData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<MonthlyVisitsData>() {}.type
                            )
                            monthlyVisitsData.statusCode = httpStatus
                            data.value = monthlyVisitsData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = MonthlyVisitsData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = MonthlyVisitsData(httpStatus)
                }
            }
        })
        return data
    }


    fun getMonthlyRevenue(): MutableLiveData<MonthlyRevenueData> {
        val data = MutableLiveData<MonthlyRevenueData>()
        val call = apiInterface!!.getMonthlyRevenue()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<MonthlyRevenueData> {
            override fun onFailure(call: Call<MonthlyRevenueData>, t: Throwable) {
                data.value = MonthlyRevenueData(Constant.getFailureCode())
                Debug.e("MonthlyRevenue isFailed", t.message)
            }

            override fun onResponse(call: Call<MonthlyRevenueData>, response: Response<MonthlyRevenueData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val monthlyRevenueData = response.body()
                        monthlyRevenueData!!.statusCode = httpStatus
                        data.value = monthlyRevenueData
                        Debug.e("MonthlyRevenue isSuccessful", Gson().toJson(monthlyRevenueData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val monthlyRevenueData = Gson().fromJson<MonthlyRevenueData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<MonthlyRevenueData>() {}.type
                            )
                            monthlyRevenueData.statusCode = httpStatus
                            data.value = monthlyRevenueData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = MonthlyRevenueData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = MonthlyRevenueData(httpStatus)
                }
            }
        })
        return data
    }

}