package com.newproject.ui.addperson.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.newproject.R
import com.newproject.ui.surname.datamodel.SurnameData


class AddPersonSpinnerAdapter : BaseAdapter() {


    lateinit var data: MutableList<SurnameData>
    lateinit var inflater: LayoutInflater
    lateinit var context: Context

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    fun customeSpinnerAdapter(context: Context, name: MutableList<SurnameData>) {
        this.data = name
        this.context = context
        inflater = LayoutInflater.from(context)
    }

    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        val view = inflater.inflate(R.layout.custom_spinner_items, null)
        view.findViewById<TextView>(R.id.textView).text = data[i].surname
        return view
    }
}