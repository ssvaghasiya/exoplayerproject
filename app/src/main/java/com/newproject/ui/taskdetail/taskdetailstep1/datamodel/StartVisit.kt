package com.newproject.ui.taskdetail.taskdetailstep1.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StartVisit {

    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

//    @SerializedName("data")
//    @Expose
//    var data_msg: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("visits")
        @Expose
        var visits: List<Visit>? = null
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

        @SerializedName("created_by")
        @Expose
        var createdBy: String? = null

        @SerializedName("created_date")
        @Expose
        var createdDate: String? = null

        @SerializedName("visit_date_time")
        @Expose
        var visitDateTime: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}