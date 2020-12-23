package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SearchVisitData {

    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("errors")
    @Expose
    var errors: Error? = null

    class Error {
        @SerializedName("search")
        @Expose
        var search: String? = null
    }

    class Data {
        @SerializedName("visits")
        @Expose
        var visits: List<TodaysVisitData.Datum>? = null

        class Visit {
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
}