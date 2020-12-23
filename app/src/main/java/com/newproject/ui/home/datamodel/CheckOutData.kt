package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckOutData {
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

        @SerializedName("employee")
        @Expose
        var employee: Employee? = null

        @SerializedName("employeeAttendance")
        @Expose
        var employeeAttendance: EmployeeAttendance? = null

        class Employee {
            @SerializedName("id")
            @Expose
            var id = 0

            @SerializedName("organization_id")
            @Expose
            var organizationId = 0

            @SerializedName("user_id")
            @Expose
            var userId = 0

            @SerializedName("team_id")
            @Expose
            var teamId: Any? = null

            @SerializedName("created_by")
            @Expose
            var createdBy = 0

            @SerializedName("type")
            @Expose
            var type: String? = null

            @SerializedName("first_name")
            @Expose
            var firstName: String? = null

            @SerializedName("last_name")
            @Expose
            var lastName: String? = null

            @SerializedName("nick_name")
            @Expose
            var nickName: String? = null

            @SerializedName("mobile_number")
            @Expose
            var mobileNumber: String? = null

            @SerializedName("email")
            @Expose
            var email: String? = null

            @SerializedName("password")
            @Expose
            var password: String? = null

            @SerializedName("duty_start")
            @Expose
            var dutyStart: String? = null

            @SerializedName("duty_end")
            @Expose
            var dutyEnd: String? = null

            @SerializedName("comments")
            @Expose
            var comments: String? = null

            @SerializedName("created_at")
            @Expose
            var createdAt: String? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: String? = null

            @SerializedName("deleted_at")
            @Expose
            var deletedAt: Any? = null

            @SerializedName("status")
            @Expose
            var status: String? = null

            @SerializedName("reason")
            @Expose
            var reason: String? = null
        }

        class EmployeeAttendance {
            @SerializedName("id")
            @Expose
            var id = 0

            @SerializedName("employee_id")
            @Expose
            var employeeId = 0

            @SerializedName("organization_id")
            @Expose
            var organizationId = 0

            @SerializedName("check_in")
            @Expose
            var checkIn: String? = null

            @SerializedName("check_in_location")
            @Expose
            var checkInLocation: String? = null

            @SerializedName("check_out")
            @Expose
            var checkOut: String? = null

            @SerializedName("check_out_location")
            @Expose
            var checkOutLocation: String? = null

            @SerializedName("break_start")
            @Expose
            var breakStart: String? = null

            @SerializedName("break_start_location")
            @Expose
            var breakStartLocation: String? = null

            @SerializedName("break_end")
            @Expose
            var breakEnd: String? = null

            @SerializedName("break_end_location")
            @Expose
            var breakEndLocation: String? = null

            @SerializedName("created_at")
            @Expose
            var createdAt: String? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: String? = null
        }
    }
}