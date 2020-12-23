package com.newproject.base.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllArea {
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

        @SerializedName("areas")
        @Expose
        var areas: List<Area>? = null
    }

    class Area {
        @SerializedName("area_id")
        @Expose
        var areaId = 0

        @SerializedName("area_name")
        @Expose
        var areaName: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null
    }
}