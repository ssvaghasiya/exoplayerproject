package com.newproject.ui.clients.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Client {
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
        var clients: List<ClientData>? = null


        class ClientData: Serializable{
            @SerializedName("lead_id")
            @Expose
            var leadId = 0

            @SerializedName("company_id")
            @Expose
            var company_id = 0

            @SerializedName("area_id")
            @Expose
            var area_id = 0

            @SerializedName("company_name")
            @Expose
            var companyName: String? = null

            @SerializedName("first_name")
            @Expose
            var firstName: String? = null

            @SerializedName("contact_number")
            @Expose
            var contactNumber: String? = null

            @SerializedName("last_name")
            @Expose
            var lastName: String? = null

            @SerializedName("area_name")
            @Expose
            var areaName: String? = null

            @SerializedName("city")
            @Expose
            var city: String? = null

            @SerializedName("organization_id")
            @Expose
            var organization_id = 0
        }
    }
}