package com.newproject.ui.clients.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemClients1Binding
import com.newproject.ui.clients.datamodel.Client

class ClientsDataAdapter() : RecyclerView.Adapter<ClientsDataAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<Client.Data.ClientData>()
    lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int, item:Client.Data.ClientData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemClients1Binding>(
            inflater,
            R.layout.item_clients1, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size
//        return 14
    }

    fun getItem(p: Int): Client.Data.ClientData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {
            holder.itemBinding.txtCompanyName.text = item.companyName
            holder.itemBinding.txtDoctorName.text = item.firstName
            holder.itemBinding.txtAreaCity.text = item.areaName + " " + item.city
            holder.itemBinding.txtContactNo.text = item.contactNumber


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position,item)
        }

    }

    fun addAll(mData: List<Client.Data.ClientData>) {
        data.clear()
        data.addAll(mData)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()

    }

    inner class MyViewHolder(internal var itemBinding: ItemClients1Binding) :
        RecyclerView.ViewHolder(itemBinding.root)
}