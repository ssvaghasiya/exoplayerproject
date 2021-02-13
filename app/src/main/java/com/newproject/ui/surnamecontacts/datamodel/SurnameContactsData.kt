package com.newproject.ui.surnamecontacts.datamodel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemSurnameBinding
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surname.utils.SurnameAdapter
import java.io.Serializable

class SurnameContactsData: Serializable {
    var address: String? = null
    var business_address: String? = null
    var business_detail: String? = null
    var id: String? = null
    var name: String? = null
    var phone: String? = null
    var surname_id: String? = null
}