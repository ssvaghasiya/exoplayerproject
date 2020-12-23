package com.newproject.ui.taskdetail.taskdetailstep2.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.ProductRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class AllProductDataModel {

    fun getAllProducts(context: Context): MutableLiveData<Product> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val productRepository = ProductRepository(apInterface)
        return productRepository.getAllProducts()
    }

}