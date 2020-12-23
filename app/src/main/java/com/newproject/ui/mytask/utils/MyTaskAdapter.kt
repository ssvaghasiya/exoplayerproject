package com.newproject.ui.mytask.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemHomeBinding
import com.newproject.ui.home.datamodel.TodaysVisitData

class MyTaskAdapter() : RecyclerView.Adapter<MyTaskAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<TodaysVisitData.Datum>()
    lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int,item: TodaysVisitData.Datum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemHomeBinding>(
            inflater,
            R.layout.item_home, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
          return data.size
    }

    fun getItem(p: Int): TodaysVisitData.Datum {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {
//            holder.itemBinding.txtCompanyName.text = item.companyName
//            holder.itemBinding.txtDoctorName.text = item.leadName
//            holder.itemBinding.txtCityArea.text = item.areaName+", "+item.city
//            if(item.status.equals(context.getString(R.string.complete))){
//                holder.itemBinding.llCompleted.visibility = View.VISIBLE
//                holder.itemBinding.llPending.visibility = View.GONE
//                holder.itemBinding.llCorporate.visibility = View.VISIBLE
//            } else if(item.status.equals(context.getString(R.string.pending))){
//                holder.itemBinding.llCompleted.visibility = View.GONE
//                holder.itemBinding.llPending.visibility = View.VISIBLE
//                holder.itemBinding.llCorporate.visibility = View.GONE
//            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position,item)
        }
    }

    fun addAll(mData: List<TodaysVisitData.Datum>) {
        data.clear()
        data.addAll(mData)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()

    }

    inner class MyViewHolder(internal var itemBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}