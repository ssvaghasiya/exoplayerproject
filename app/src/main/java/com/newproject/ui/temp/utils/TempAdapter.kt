package com.newproject.ui.temp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemViewAddSportsBinding
import com.newproject.ui.temp.datamodel.TempData
import java.util.*

class TempAdapter() : RecyclerView.Adapter<TempAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<TempData>()
    lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int)
        fun onGetSelected(size:Int?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemViewAddSportsBinding>(
            inflater,
            R.layout.item_view_add_sports, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size

    }

    fun getItem(p: Int): TempData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {

            holder.itemBinding.txtSportsName.text = item.name
            holder.itemBinding.imgSportsSelected.visibility =
                if (item.isChecked!!) View.VISIBLE else View.GONE

            holder.itemBinding.txtSportsName.setOnClickListener {
                item.isChecked = !item.isChecked!!
                holder.itemBinding.imgSportsSelected.visibility =
                    if (item.isChecked!!) View.VISIBLE else View.GONE
                mEventListener.onGetSelected(getSelected()!!.size)

            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position)
        }

    }

    fun addAll(mData: List<TempData>?) {
        data.clear()
        data.addAll(mData!!)
        notifyDataSetChanged()
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(internal var itemBinding: ItemViewAddSportsBinding) :
        RecyclerView.ViewHolder(
            itemBinding.root
        )

    fun getSelected(): ArrayList<TempData>? {
        val selected: ArrayList<TempData> = ArrayList<TempData>()
        for (i in data.indices) {
            if (data[i].isChecked == true) {
                selected.add(data[i])
            }
        }
        return selected
    }
}