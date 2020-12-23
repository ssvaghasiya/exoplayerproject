package com.newproject.ui.taskdetail.taskdetailstep2.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

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

        @SerializedName("products")
        @Expose
        var products: List<ProductData>? = null
    }

    class ProductData {
        @SerializedName("product_id")
        @Expose
        var productId = 0

        @SerializedName("family_product_id")
        @Expose
        var familyProductId = 0

        @SerializedName("name")
        @Expose
        var name: String? = null
    }
}