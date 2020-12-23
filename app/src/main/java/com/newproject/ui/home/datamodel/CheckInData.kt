package com.newproject.ui.home.datamodel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CheckInData {
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    constructor(statusCode: Int) {
        this.statusCode = statusCode
    }

    @SerializedName("data")
    @Expose
    var data: CheckOutData.Data? = null

}