package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MonthlyVisitsData {
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

        @SerializedName("visit_count")
        @Expose
        var visitCount = 0

        @SerializedName("target_visits")
        @Expose
        var targetVisits = 0
    }
}