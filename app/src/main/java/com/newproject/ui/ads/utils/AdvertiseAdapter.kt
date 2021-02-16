package com.newproject.ui.ads.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.databinding.ItemGalleryBinding
import com.newproject.ui.gallery.datamodel.GalleryData

class AdvertiseAdapter() : RecyclerView.Adapter<AdvertiseAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<GalleryData>()
    lateinit var context: Context


    constructor(context: Context) : this() {
        this.context = context

    }

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }


    interface EventListener {
        fun onItemClick(pos: Int, item: GalleryData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = DataBindingUtil.inflate<ItemGalleryBinding>(
            inflater,
            R.layout.item_gallery, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size

    }

    fun getItem(p: Int): GalleryData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {

            val urlLogo = item.image
            Debug.e(item.image)
            Utils.loadImage(holder.itemBinding.imageGallery, urlLogo!!,context, R.drawable.placeholder)
            holder.itemBinding.textviewGallary.visibility = View.GONE

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position,item)
        }

    }

    fun addAll(mData: List<GalleryData>?) {
        data.clear()
        data.addAll(mData!!)
        notifyDataSetChanged()
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(internal var itemBinding: ItemGalleryBinding) : RecyclerView.ViewHolder(itemBinding.root)
}