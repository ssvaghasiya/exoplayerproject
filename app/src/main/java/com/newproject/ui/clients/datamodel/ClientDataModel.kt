package com.newproject.ui.clients.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.ClientRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface
import java.io.IOException

class ClientDataModel {

    fun getClients(context: Context): MutableLiveData<Client> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val clientRepository = ClientRepository(apInterface)
        return clientRepository.getClients()
    }

    var leadId: String? = null

    fun getClientDetails(context: Context): MutableLiveData<ClientDetails> {
        val data: MutableMap<String, String> = HashMap()
        if (leadId != null) {
            try {
                data["lead_id"] = leadId!!

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val clientRepository = ClientRepository(apInterface)
        return clientRepository.getClientDetails(data)
    }
}