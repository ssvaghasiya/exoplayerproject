package com.newproject.ui.clients.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.ClientRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import java.io.IOException

class VisitHistoryDataModel {

    var client_id: String? = null

    fun getVisitHistory(context: Context): MutableLiveData<VisitHistoryData> {

        val data: MutableMap<String, String> = HashMap()
        if (client_id != null) {
            try {
                data["client_id"] = client_id!!
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val apInterface: APIinterface = APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val clientRepository = ClientRepository(apInterface)
        return clientRepository.getVisitHistory(data)
    }
}