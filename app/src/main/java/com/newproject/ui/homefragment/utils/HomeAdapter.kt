package com.newproject.ui.homefragment.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.databinding.ItemHomeBinding

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<HomeData>()
    lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int)
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

    fun getItem(p: Int): HomeData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {

            holder.itemBinding.imageHome.setImageResource(item.imageResource)
            holder.itemBinding.txtHome.text = item.text

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position)
        }

    }

    fun addAll(mData: List<HomeData>?) {
        data.clear()
        data.addAll(mData!!)
        notifyDataSetChanged()
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(internal var itemBinding: ItemHomeBinding) : RecyclerView.ViewHolder(itemBinding.root)
}