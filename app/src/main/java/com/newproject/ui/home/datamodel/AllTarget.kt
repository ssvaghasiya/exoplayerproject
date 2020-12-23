package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllTarget {
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

        @SerializedName("targets")
        @Expose
        var targets: Targets? = null
    }

    class Targets {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("monthly_revenue")
        @Expose
        var monthlyRevenue = 0

        @SerializedName("quaterly_revenue")
        @Expose
        var quaterlyRevenue = 0

        @SerializedName("yearly_revenue")
        @Expose
        var yearlyRevenue = 0

        @SerializedName("monthly_visits")
        @Expose
        var monthlyVisits = 0

        @SerializedName("quaterly_visits")
        @Expose
        var quaterlyVisits = 0

        @SerializedName("yearly_visits")
        @Expose
        var yearlyVisits = 0

        @SerializedName("successful_monthly_visits")
        @Expose
        var successfulMonthlyVisits = 0

        @SerializedName("successful_quaterly_visits")
        @Expose
        var successfulQuaterlyVisits = 0

        @SerializedName("successful_yearly_visits")
        @Expose
        var successfulYearlyVisits = 0

        @SerializedName("employee_id")
        @Expose
        var employeeId = 0

        @SerializedName("org_id")
        @Expose
        var orgId = 0

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("deleted_at")
        @Expose
        var deletedAt: Any? = null
    }
}