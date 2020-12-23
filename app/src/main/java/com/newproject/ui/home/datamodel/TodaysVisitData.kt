package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TodaysVisitData {
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    class Datum: Serializable {
        @SerializedName("visit_id")
        @Expose
        var visitId = 0

        @SerializedName("visit_topic")
        @Expose
        var visitTopic: String? = null

        @SerializedName("company_name")
        @Expose
        var companyName: String? = null

        @SerializedName("visit_start")
        @Expose
        var visitStart: String? = null

        @SerializedName("visit_start_location")
        @Expose
        var visitStartLocation: String? = null

        @SerializedName("visit_end")
        @Expose
        var visitEnd: Any? = null

        @SerializedName("visit_end_location")
        @Expose
        var visitEndLocation: Any? = null

        @SerializedName("lead_name")
        @Expose
        var leadName: String? = null

        @SerializedName("area_name")
        @Expose
        var areaName: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null

        @SerializedName("market_material")
        @Expose
        var marketMaterial: Any? = null

        @SerializedName("doctor_response")
        @Expose
        var doctorResponse: Any? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("reason")
        @Expose
        var reason: String? = null

        @SerializedName("visit_date")
        @Expose
        var visitDate: String? = null

        @SerializedName("visit_time")
        @Expose
        var visitTime: String? = null

    }

}