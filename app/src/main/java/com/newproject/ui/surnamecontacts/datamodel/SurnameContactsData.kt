package com.newproject.ui.surnamecontacts.datamodel

import java.io.Serializable

class SurnameContactsData: Serializable {
    var address: String? = null
    var business_address: String? = null
    var business_detail: String? = null
    var id: String? = null
    var name: String? = null
    var phone: String? = null
    var surname_id: String? = null


    override fun toString(): String {
        return phone!!
    }
}