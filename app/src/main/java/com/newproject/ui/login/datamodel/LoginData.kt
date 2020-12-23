package com.newproject.ui.login.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginData {
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("errors")
    @Expose
    var errors: Errors? = null

    class Errors {
        @SerializedName("email")
        @Expose
        var email: String? = null
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

        @SerializedName("employee")
        @Expose
        var employee: Employee? = null

        @SerializedName("token")
        @Expose
        var token: String? = null


        class Employee {
            @SerializedName("id")
            @Expose
            var id = 0

            @SerializedName("org_id")
            @Expose
            var orgId = 0

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("email")
            @Expose
            var email: String? = null

            @SerializedName("type")
            @Expose
            var type: String? = null

            @SerializedName("organization_name")
            @Expose
            var organizationName: String? = null
        }
    }
}