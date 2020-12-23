package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.network.APIinterface
import com.newproject.ui.clients.datamodel.Client
import com.newproject.ui.clients.datamodel.ClientDetails
import com.newproject.ui.clients.datamodel.VisitHistoryData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun getClients(): MutableLiveData<Client> {
        val data = MutableLiveData<Client>()
        val call = apiInterface!!.getClients()
        Debug.e("API", call.request().url().encodedPath())
//        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<Client> {
            override fun onFailure(call: Call<Client>, t: Throwable) {
                data.value = Client(Constant.getFailureCode())
                Debug.e("get All Clients isFailed", t.message)
            }

            override fun onResponse(call: Call<Client>, response: Response<Client>) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("get All Clients isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<Client>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<Client>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = Client(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = Client(resDataStatus)
                }
            }
        })
        return data
    }

    fun getClientDetails(param: MutableMap<String, String>): MutableLiveData<ClientDetails> {
        val data = MutableLiveData<ClientDetails>()
        val call = apiInterface!!.getClientDetails(param)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(data))
        call.enqueue(object : Callback<ClientDetails> {
            override fun onFailure(call: Call<ClientDetails>, t: Throwable) {
                data.value = ClientDetails(Constant.getFailureCode())
                Debug.e("getClientDetails isFailed", t.message)
            }

            override fun onResponse(call: Call<ClientDetails>, response: Response<ClientDetails>) {
                var resDataStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val resData = response.body()
                        resData!!.statusCode = resDataStatus
                        data.value = resData
                        Debug.e("getClientDetails isSuccessful", Gson().toJson(resData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val resData = Gson().fromJson<ClientDetails>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<ClientDetails>() {}.type
                            )
                            resData.statusCode = resDataStatus
                            data.value = resData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = ClientDetails(resDataStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = ClientDetails(resDataStatus)
                }
            }
        })
        return data
    }

    fun getVisitHistory(param: MutableMap<String, String>): MutableLiveData<VisitHistoryData> {
        val data = MutableLiveData<VisitHistoryData>()
        val call = apiInterface!!.getVisitHistory(param)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(param))
        call.enqueue(object : Callback<VisitHistoryData> {
            override fun onFailure(call: Call<VisitHistoryData>, t: Throwable) {
                data.value = VisitHistoryData(Constant.getFailureCode())
                Debug.e("getVisitHistory isFailed", t.message)
            }

            override fun onResponse(call: Call<VisitHistoryData>, response: Response<VisitHistoryData>) {
                var httpResponse = response.code()
                try {
                    if (response.isSuccessful) {
                        val visitHistoryData = response.body()
                        visitHistoryData!!.statusCode = httpResponse
                        data.value = visitHistoryData
                        Debug.e("getVisitHistory isSuccessful", Gson().toJson(visitHistoryData))
                    } else {
                        try {
                            val inputAsString =
                                response.errorBody()!!.source().inputStream().bufferedReader()
                                    .use { it.readText() }
                            Debug.e("Input", inputAsString)
                            val sb = StringBuilder()
                            sb.append(inputAsString)
                            val visitHistoryData = Gson().fromJson<VisitHistoryData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<VisitHistoryData>() {}.type
                            )
                            visitHistoryData.statusCode = httpResponse
                            data.value = visitHistoryData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = VisitHistoryData(httpResponse)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = VisitHistoryData(httpResponse)
                }
            }
        })
        return data
    }

}