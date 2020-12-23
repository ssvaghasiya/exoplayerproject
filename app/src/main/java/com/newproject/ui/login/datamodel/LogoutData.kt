package com.newproject.ui.login.datamodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LogoutData {
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("message")
    @Expose
    var message: String? = null
}