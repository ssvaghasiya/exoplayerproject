package com.newproject.ui.createnewtask.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CreateTask {
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
        @SerializedName("visit")
        @Expose
        var visit: List<Visit>? = null
    }

    class Visit {
        @SerializedName("visit_id")
        @Expose
        var visitId = 0

        @SerializedName("company_name")
        @Expose
        var companyName: String? = null

        @SerializedName("lead_name")
        @Expose
        var leadName: String? = null

        @SerializedName("area_name")
        @Expose
        var areaName: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null

        @SerializedName("doctor_response")
        @Expose
        var doctorResponse: Any? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}