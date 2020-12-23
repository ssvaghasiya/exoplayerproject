package com.newproject.ui.clients.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class VisitHistoryData {

    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    class Data {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("visitsHistory")
        @Expose
        var visitsHistory: List<VisitsHistory>? = null

        class VisitsHistory {
            @SerializedName("visit_id")
            @Expose
            var visitId = 0

            @SerializedName("topic")
            @Expose
            var topic: Any? = null

            @SerializedName("date")
            @Expose
            var date: String? = null

            @SerializedName("status")
            @Expose
            var status: String? = null
        }
    }
}