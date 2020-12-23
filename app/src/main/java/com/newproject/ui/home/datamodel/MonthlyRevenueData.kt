package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MonthlyRevenueData {
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

        @SerializedName("target_monthly_revenue")
        @Expose
        var targetMonthlyRevenue = 0

        @SerializedName("monthly_revenue")
        @Expose
        var monthlyRevenue = 0
    }
}