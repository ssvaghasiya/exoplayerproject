package com.newproject.datasource

import androidx.lifecycle.MutableLiveData
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.base.datamodel.AllArea
import com.newproject.network.APIinterface
import com.newproject.ui.login.datamodel.LoginData
import com.newproject.ui.login.datamodel.LoginDataModel
import com.newproject.ui.login.datamodel.LogoutData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(apiInterface: APIinterface) {

    var apiInterface: APIinterface? = apiInterface

    fun login(loginDataModel: LoginDataModel): MutableLiveData<LoginData> {
        val data = MutableLiveData<LoginData>()
        val call = apiInterface!!.login(loginDataModel)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(loginDataModel))
        call.enqueue(object : Callback<LoginData> {
            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                data.value = LoginData(Constant.getFailureCode())
                Debug.e("login isFailed", t.message)
            }

            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                var loginStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val loginData = response.body()
                        loginData!!.statusCode = loginStatus
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
                            val loginData = Gson().fromJson<LoginData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<LoginData>() {}.type
                            )
                            loginData.statusCode = loginStatus
                            data.value = loginData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = LoginData(loginStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = LoginData(loginStatus)
                }
            }
        })
        return data
    }

    fun logOut(): MutableLiveData<LogoutData> {
        val data = MutableLiveData<LogoutData>()
        val call = apiInterface!!.logOut()
        Debug.e("API", call.request().url().encodedPath())
        call.enqueue(object : Callback<LogoutData> {
            override fun onFailure(call: Call<LogoutData>, t: Throwable) {
                data.value = LogoutData(Constant.getFailureCode())
                Debug.e("login isFailed", t.message)
            }

            override fun onResponse(call: Call<LogoutData>, response: Response<LogoutData>) {
                var loginStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val loginData = response.body()
                        loginData!!.statusCode = loginStatus
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
                            val loginData = Gson().fromJson<LogoutData>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<LogoutData>() {}.type
                            )
                            loginData.statusCode = loginStatus
                            data.value = loginData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = LogoutData(loginStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = LogoutData(loginStatus)
                }
            }
        })
        return data
    }

    fun getArea(id: String): MutableLiveData<AllArea> {
        val data = MutableLiveData<AllArea>()
        val call = apiInterface!!.getArea(id)
        Debug.e("API", call.request().url().encodedPath())
        Debug.e("body", Gson().toJson(id))
        call.enqueue(object : Callback<AllArea> {
            override fun onFailure(call: Call<AllArea>, t: Throwable) {
                data.value = AllArea(Constant.getFailureCode())
                Debug.e("login isFailed", t.message)
            }

            override fun onResponse(call: Call<AllArea>, response: Response<AllArea>) {
                var loginStatus = response.code()
                try {
                    if (response.isSuccessful) {
                        val loginData = response.body()
                        loginData!!.statusCode = loginStatus
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
                            val loginData = Gson().fromJson<AllArea>(
                                JSONObject(inputAsString).toString(),
                                object : TypeToken<AllArea>() {}.type
                            )
                            loginData.statusCode = loginStatus
                            data.value = loginData
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.value = AllArea(loginStatus)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    data.value = AllArea(loginStatus)
                }
            }
        })
        return data
    }
}