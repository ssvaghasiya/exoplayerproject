package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SuccessfulMonthlyVisitsData {

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
        @SerializedName("target_successful_visits")
        @Expose
        var targetSuccessfulVisits = 0

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("successful_visit_count")
        @Expose
        var successfulVisitCount = 0
    }
}