package com.newproject.ui.taskdetail.taskdetaildesc.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TaskDetails {

    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("visits")
        @Expose
        var visits: Visit? = null

        @SerializedName("visitProducts")
        @Expose
        var visitProducts: List<VisitProduct>? = null
    }

    class Visit : Serializable {
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

        @SerializedName("visit_start")
        @Expose
        var visitStart: String? = null

        @SerializedName("visit_end")
        @Expose
        var visitEnd: Any? = null

        @SerializedName("visit_end_location")
        @Expose
        var visitEndLocation: Any? = null

        @SerializedName("market_material")
        @Expose
        var marketMaterial: Any? = null

        @SerializedName("reason")
        @Expose
        var reason: String? = null

        @SerializedName("visit_time")
        @Expose
        var visitTime: String? = null

        @SerializedName("doctor_response")
        @Expose
        var doctorResponse: Any? = null

        @SerializedName("created_by")
        @Expose
        var createdBy: String? = null

        @SerializedName("created_date")
        @Expose
        var createdDate: String? = null

        @SerializedName("visit_date")
        @Expose
        var visitDate: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("visit_topic")
        @Expose
        var visitTopic: Any? = null
    }

    class VisitProduct {
        @SerializedName("product_id")
        @Expose
        var productId = 0

        @SerializedName("product_type")
        @Expose
        var productType: String? = null

        @SerializedName("retail_unit_price")
        @Expose
        var retailUnitPrice: String? = null

        @SerializedName("is_sample")
        @Expose
        var isSample = 0

        @SerializedName("quantity")
        @Expose
        var quantity: Any? = null
    }
}