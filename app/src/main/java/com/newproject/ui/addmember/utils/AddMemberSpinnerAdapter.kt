package com.newproject.ui.addmember.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.Nullable
import com.newproject.R
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData


class AddMemberSpinnerAdapter: ArrayAdapter<SurnameContactsData> {

    var list: ArrayList<SurnameContactsData>? = null
    lateinit var inflater: LayoutInflater

    constructor(context: Context, list: ArrayList<SurnameContactsData>?): super(
        context, R.layout.custom_spinner_items,
        list!!
    ) {
        this.list = list
        inflater = LayoutInflater.from(context)

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent);
    }

    @Nullable
    override fun getItem(position: Int): SurnameContactsData? {
        return super.getItem(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.custom_spinner_items, null)
        view.findViewById<TextView>(R.id.textView).text =  list!![position].phone
        return view
    }

    fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.custom_spinner_items, null)
        view.findViewById<TextView>(R.id.textView).text =  list!![position].phone
        return view
    }
}