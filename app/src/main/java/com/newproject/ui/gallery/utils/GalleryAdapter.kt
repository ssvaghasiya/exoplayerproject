package com.newproject.ui.gallery.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.newproject.R
import com.newproject.databinding.ItemGalleryBinding
import com.newproject.databinding.ItemVideosBinding
import com.newproject.ui.gallery.datamodel.GalleryData

class GalleryAdapter() : RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

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

            Glide.with(context).load(item.imageResource)
                .placeholder(context.getDrawable(R.drawable.place_holder))
                .error(context.getDrawable(R.drawable.place_holder))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemBinding.imageGallery)
            holder.itemBinding.textviewGallary.text = item.text

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