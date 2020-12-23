package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.network.APIinterface
import com.newproject.ui.createnewtask.datamodel.CreateTask
import com.newproject.ui.home.datamodel.AllVisitsData
import com.newproject.ui.home.datamodel.SearchVisitData
import com.newproject.ui.home.datamodel.TodaysVisitData
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDetails
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisit
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.TaskStep2Details
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun createTask(param: MultipartBody): MutableLiveData<CreateTask> {
        val data = MutableLiveData<CreateTask>()
        val call = apiInterface!!.createTask(param)
        Debug.e("API", call.request().url().encodedPath())
//        Debug.e("body", Gson().toJson(createTaskDataModel))
        call.enqueue(object : Callback<CreateTask> {
            override fun onFailure(call: Call<CreateTask>, t: Throwable) {
                data.value = CreateTask(Constant.getFailureCode())
                Debug.e("createTask isFailed", t.message)
            }

            override fun onResponse(call: Call<CreateTask>, response: Response<CreateTask>) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("createTask isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<CreateTask>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<CreateTask>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = CreateTask(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = CreateTask(resDataStatus)
                }
            }
        })
        return data
    }

    fun getTask(param: MutableMap<String, String>): MutableLiveData<TaskDetails> {
        val data = MutableLiveData<TaskDetails>()
        val call = apiInterface!!.getTask(param)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<TaskDetails> {
            override fun onFailure(call: Call<TaskDetails>, t: Throwable) {
                data.value = TaskDetails(Constant.getFailureCode())
                Debug.e("getTask isFailed", t.message)
            }

            override fun onResponse(call: Call<TaskDetails>, response: Response<TaskDetails>) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("getTask isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<TaskDetails>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<TaskDetails>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = TaskDetails(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = TaskDetails(resDataStatus)
                }
            }
        })
        return data
    }

    fun getAllVisits(): MutableLiveData<AllVisitsData> {
        val data = MutableLiveData<AllVisitsData>()
        val call = apiInterface!!.getAllVisits()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<AllVisitsData> {
            override fun onFailure(call: Call<AllVisitsData>, t: Throwable) {
                data.value = AllVisitsData(Constant.getFailureCode())
                Debug.e("All Visits isFailed", t.message)
            }

            override fun onResponse(call: Call<AllVisitsData>, response: Response<AllVisitsData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val allVisitsData = response.body()
                        allVisitsData!!.statusCode = httpStatus
                        data.value = allVisitsData
                        Debug.e("All Visits isSuccessful", Gson().toJson(allVisitsData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val allVisitsData = Gson().fromJson<AllVisitsData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<AllVisitsData>() {}.type
                            )
                            allVisitsData.statusCode = httpStatus
                            data.value = allVisitsData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = AllVisitsData(httpStatus)
                            Debug.e("All Visits isFailed", e.message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = AllVisitsData(httpStatus)
                    Debug.e("All Visits isFailed", e.message)
                }
            }
        })
        return data
    }


    fun getTodaysVisits(): MutableLiveData<TodaysVisitData> {
        val data = MutableLiveData<TodaysVisitData>()
        val call = apiInterface!!.getTodaysVisits()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<TodaysVisitData> {
            override fun onFailure(call: Call<TodaysVisitData>, t: Throwable) {
                data.value = TodaysVisitData(Constant.getFailureCode())
                Debug.e("Todays Visits isFailed", t.message)
            }

            override fun onResponse(
                call: Call<TodaysVisitData>,
                response: Response<TodaysVisitData>
            ) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val allVisitsData = response.body()
                        allVisitsData!!.statusCode = httpStatus
                        data.value = allVisitsData
                        Debug.e("Todays Visits isSuccessful", Gson().toJson(allVisitsData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val allVisitsData = Gson().fromJson<TodaysVisitData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<TodaysVisitData>() {}.type
                            )
                            allVisitsData.statusCode = httpStatus
                            data.value = allVisitsData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = TodaysVisitData(httpStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = TodaysVisitData(httpStatus)
                }
            }
        })
        return data
    }

    fun startVisit(param: MutableMap<String, String>): MutableLiveData<StartVisit> {
        val data = MutableLiveData<StartVisit>()
        val call = apiInterface!!.startVisit(param)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<StartVisit> {
            override fun onFailure(call: Call<StartVisit>, t: Throwable) {
                data.value = StartVisit(Constant.getFailureCode())
                Debug.e("startVisit isFailed", t.message)
            }

            override fun onResponse(call: Call<StartVisit>, response: Response<StartVisit>) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("startVisit isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<StartVisit>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<StartVisit>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = StartVisit(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = StartVisit(resDataStatus)
                }
            }
        })
        return data
    }

    fun updateTaskStep2(
        visitId: String,
        param: RequestBody
    ): MutableLiveData<TaskStep2Details> {
        val data = MutableLiveData<TaskStep2Details>()
        val call = apiInterface!!.updateVisit(visitId, param)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(param))
        call.enqueue(object : Callback<TaskStep2Details> {
            override fun onFailure(call: Call<TaskStep2Details>, t: Throwable) {
                data.value = TaskStep2Details(Constant.getFailureCode())
                Debug.e("updateTaskStep2 isFailed", t.message)
            }

            override fun onResponse(
                call: Call<TaskStep2Details>,
                response: Response<TaskStep2Details>
            ) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("updateTaskStep2 isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<TaskStep2Details>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<TaskStep2Details>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = TaskStep2Details(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = TaskStep2Details(resDataStatus)
                }
            }
        })
        return data
    }

    fun getSearchVisit(param: MutableMap<String, String>): MutableLiveData<SearchVisitData> {
        val data = MutableLiveData<SearchVisitData>()
        val call = apiInterface!!.getSearchVisit(param)
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<SearchVisitData> {
            override fun onFailure(call: Call<SearchVisitData>, t: Throwable) {
                data.value = SearchVisitData(Constant.getFailureCode())
                Debug.e("Search Visit isFailed", t.message)
            }

            override fun onResponse(call: Call<SearchVisitData>, response: Response<SearchVisitData>) {
                var httpStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val searchVisitData = response.body()
                        searchVisitData!!.statusCode = httpStatus
                        data.value = searchVisitData
                        Debug.e("Search Visit isSuccessful", Gson().toJson(searchVisitData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val searchVisitData = Gson().fromJson<SearchVisitData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<SearchVisitData>() {}.type
                            )
                            searchVisitData.statusCode = httpStatus
                            data.value = searchVisitData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = SearchVisitData(httpStatus)
                            Debug.e("Search Visit isFailed", e.message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = SearchVisitData(httpStatus)
                    Debug.e("Search Visit isFailed", e.message)
                }
            }
        })
        return data
    }
}