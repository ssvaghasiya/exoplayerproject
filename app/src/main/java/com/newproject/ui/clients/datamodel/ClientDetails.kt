package com.newproject.ui.clients.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClientDetails {
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("clients")
        @Expose
        var clients: List<Client.Data.ClientData>? = null
    }

}