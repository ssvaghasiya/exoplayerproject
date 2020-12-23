package com.newproject.ui.clients.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemClients2VisitHistoryBinding
import com.newproject.ui.clients.datamodel.VisitHistoryData
import java.text.SimpleDateFormat
import java.util.*

class HistoryDataAdapter() : RecyclerView.Adapter<HistoryDataAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<VisitHistoryData.Data.VisitsHistory>()
    lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int,item: VisitHistoryData.Data.VisitsHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemClients2VisitHistoryBinding>(
            inflater,
            R.layout.item_clients2_visit_history, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun getItem(p: Int): VisitHistoryData.Data.VisitsHistory {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {
            holder.itemBinding.txtPupose.text = item.topic.toString()
            holder.itemBinding.txtDateTime.text = parseDate(item.date)
            if(item.status.equals(context.getString(R.string.completed))){
                holder.itemBinding.llCompleted.visibility = View.VISIBLE
                holder.itemBinding.llPending.visibility = View.GONE
            } else if(item.status.equals(context.getString(R.string.pending))){
                holder.itemBinding.llCompleted.visibility = View.GONE
                holder.itemBinding.llPending.visibility = View.VISIBLE
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position,item)
        }

    }

    fun addAll(mData: List<VisitHistoryData.Data.VisitsHistory>) {
        data.clear()
        data.addAll(mData)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()

    }

    inner class MyViewHolder(internal var itemBinding: ItemClients2VisitHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("SimpleDateFormat")
    fun parseDate(time: String?): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "MMM dd, yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time!!)
            str = outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }
}