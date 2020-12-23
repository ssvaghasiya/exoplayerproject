package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllVisitsData {


    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }
    @SerializedName("current_page")
    @Expose
    var currentPage = 0

    @SerializedName("data")
    @Expose
    var data: List<TodaysVisitData.Datum>? = null

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String? = null

    @SerializedName("from")
    @Expose
    var from = 0

    @SerializedName("last_page")
    @Expose
    var lastPage = 0

    @SerializedName("last_page_url")
    @Expose
    var lastPageUrl: String? = null

    @SerializedName("links")
    @Expose
    var links: List<Link>? = null

    @SerializedName("next_page_url")
    @Expose
    var nextPageUrl: String? = null

    @SerializedName("path")
    @Expose
    var path: String? = null

    @SerializedName("per_page")
    @Expose
    var perPage = 0

    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: Any? = null

    @SerializedName("to")
    @Expose
    var to = 0

    @SerializedName("total")
    @Expose
    var total = 0

//    class Datum: Serializable {
//        @SerializedName("visit_id")
//        @Expose
//        var visitId = 0
//
//        @SerializedName("visit_topic")
//        @Expose
//        var visitTopic: String? = null
//
//        @SerializedName("company_name")
//        @Expose
//        var companyName: String? = null
//
//        @SerializedName("lead_name")
//        @Expose
//        var leadName: String? = null
//
//        @SerializedName("area_name")
//        @Expose
//        var areaName: String? = null
//
//        @SerializedName("city")
//        @Expose
//        var city: String? = null
//
//        @SerializedName("market_material")
//        @Expose
//        var marketMaterial: Any? = null
//
//        @SerializedName("doctor_response")
//        @Expose
//        var doctorResponse: String? = null
//
//        @SerializedName("status")
//        @Expose
//        var status: String? = null
//
//        @SerializedName("reason")
//        @Expose
//        var reason: String? = null
//
//        @SerializedName("visit_date")
//        @Expose
//        var visitDate: String? = null
//
//        @SerializedName("visit_time")
//        @Expose
//        var visitTime: String? = null
//    }

    class Link {
        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("active")
        @Expose
        var active = false
    }
}