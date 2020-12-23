package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.network.APIinterface
import com.newproject.ui.home.datamodel.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun getCheckIn(): MutableLiveData<CheckInData> {
        val data = MutableLiveData<CheckInData>()
        val call = apiInterface!!.getCheckIn()
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<CheckInData> {
            override fun onFailure(call: Call<CheckInData>, t: Throwable) {
                data.value = CheckInData(Constant.getFailureCode())
                Debug.e("CheckIn isFailed", t.message)
            }

            override fun onResponse(call: Call<CheckInData>, response: Response<CheckInData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val checkInData = response.body()
                        checkInData!!.statusCode = httpStatus
                        data.value = checkInData
                        Debug.e("CheckIn isSuccessful", Gson().toJson(checkInData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val checkInData = Gson().fromJson<CheckInData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<CheckInData>() {}.type
                            )
                            checkInData.statusCode = httpStatus
                            data.value = checkInData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = CheckInData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = CheckInData(httpStatus)
                }
            }
        })
        return data
    }

    fun getCheckOut(): MutableLiveData<CheckOutData> {
        val data = MutableLiveData<CheckOutData>()
        val call = apiInterface!!.getCheckOut()
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<CheckOutData> {
            override fun onFailure(call: Call<CheckOutData>, t: Throwable) {
                data.value = CheckOutData(Constant.getFailureCode())
                Debug.e("Checkout isFailed", t.message)
            }

            override fun onResponse(call: Call<CheckOutData>, response: Response<CheckOutData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val checkOutData = response.body()
                        checkOutData!!.statusCode = httpStatus
                        data.value = checkOutData
                        Debug.e("Checkout isSuccessful", Gson().toJson(checkOutData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val checkOutData = Gson().fromJson<CheckOutData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<CheckOutData>() {}.type
                            )
                            checkOutData.statusCode = httpStatus
                            data.value = checkOutData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = CheckOutData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = CheckOutData(httpStatus)
                }
            }
        })
        return data
    }

    fun getBreakStart(): MutableLiveData<BreakStartData> {
        val data = MutableLiveData<BreakStartData>()
        val call = apiInterface!!.getBreakStart()
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<BreakStartData> {
            override fun onFailure(call: Call<BreakStartData>, t: Throwable) {
                data.value = BreakStartData(Constant.getFailureCode())
                Debug.e("BreakStart isFailed", t.message)
            }

            override fun onResponse(
                call: Call<BreakStartData>,
                response: Response<BreakStartData>
            ) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val breakStartData = response.body()
                        breakStartData!!.statusCode = httpStatus
                        data.value = breakStartData
                        Debug.e("BreakStart isSuccessful", Gson().toJson(breakStartData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val breakStartData = Gson().fromJson<BreakStartData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<BreakStartData>() {}.type
                            )
                            breakStartData.statusCode = httpStatus
                            data.value = breakStartData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = BreakStartData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = BreakStartData(httpStatus)
                }
            }
        })
        return data
    }

    fun getBreakEnd(): MutableLiveData<BreakEndData> {
        val data = MutableLiveData<BreakEndData>()
        val call = apiInterface!!.getBreakEnd()
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<BreakEndData> {
            override fun onFailure(call: Call<BreakEndData>, t: Throwable) {
                data.value = BreakEndData(Constant.getFailureCode())
                Debug.e("BreakStart isFailed", t.message)
            }

            override fun onResponse(
                call: Call<BreakEndData>,
                response: Response<BreakEndData>
            ) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val breakEndData = response.body()
                        breakEndData!!.statusCode = httpStatus
                        data.value = breakEndData
                        Debug.e("BreakStart isSuccessful", Gson().toJson(breakEndData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val breakEndData = Gson().fromJson<BreakEndData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<BreakEndData>() {}.type
                            )
                            breakEndData.statusCode = httpStatus
                            data.value = breakEndData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = BreakEndData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = BreakEndData(httpStatus)
                }
            }
        })
        return data
    }

    fun getEmployeeAttendance(): MutableLiveData<AttendanceData> {
        val data = MutableLiveData<AttendanceData>()
        val call = apiInterface!!.getEmployeeAttendance()
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<AttendanceData> {
            override fun onFailure(call: Call<AttendanceData>, t: Throwable) {
                data.value = AttendanceData(Constant.getFailureCode())
                Debug.e("getEmployeeAttendance isFailed", t.message)
            }

            override fun onResponse(
                call: Call<AttendanceData>,
                response: Response<AttendanceData>
            ) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val breakEndData = response.body()
                        breakEndData!!.statusCode = httpStatus
                        data.value = breakEndData
                        Debug.e("getEmployeeAttendance isSuccessful", Gson().toJson(breakEndData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val breakEndData = Gson().fromJson<AttendanceData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<AttendanceData>() {}.type
                            )
                            breakEndData.statusCode = httpStatus
                            data.value = breakEndData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = AttendanceData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = AttendanceData(httpStatus)
                }
            }
        })
        return data
    }

}