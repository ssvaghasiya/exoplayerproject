package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AttendanceData {

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
        @SerializedName("employee_attendance")
        @Expose
        var employeeAttendance: EmployeeAttendance? = null

        class EmployeeAttendance {
            @SerializedName("check_in")
            @Expose
            var checkIn = 0

            @SerializedName("break_status")
            @Expose
            var breakStatus = 0

            @SerializedName("break_start_time")
            @Expose
            var breakStartTime: String? = null
        }
    }
}