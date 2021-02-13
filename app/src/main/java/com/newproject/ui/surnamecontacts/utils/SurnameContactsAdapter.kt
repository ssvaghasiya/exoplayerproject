package com.newproject.ui.surnamecontacts.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemContactsBinding
import com.newproject.databinding.ItemSurnameBinding
import com.newproject.ui.surname.utils.SurnameAdapter
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData

class SurnameContactsAdapter() : RecyclerView.Adapter<SurnameContactsAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<SurnameContactsData>()
    lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int, item: SurnameContactsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemContactsBinding>(
            inflater,
            R.layout.item_contacts, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size

    }

    fun getItem(p: Int): SurnameContactsData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {

            holder.itemBinding.txtIndex.text = (position + 1).toString()+"."
            holder.itemBinding.txtPersonNamee.text = item.name
            holder.itemBinding.txtNumber.text = item.phone
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position,item)
        }

    }

    fun addAll(mData: List<SurnameContactsData>?) {
        data.clear()
        data.addAll(mData!!)
        notifyDataSetChanged()
    }


    fun add(mData: SurnameContactsData) {
        data.add(mData)
        notifyDataSetChanged()
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(internal var itemBinding: ItemContactsBinding) : RecyclerView.ViewHolder(itemBinding.root)
}