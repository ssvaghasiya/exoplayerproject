package com.newproject.ui.memberdetails.utils

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
import com.newproject.databinding.ItemMembersBinding
import com.newproject.ui.ads.utils.AdvertiseAdapter
import com.newproject.ui.persondetail.datamodel.PersonDetailData

class MembersAdapter() : RecyclerView.Adapter<MembersAdapter.MyViewHolder>() {

    private lateinit var mEventListener: EventListener

    private var data = mutableListOf<PersonDetailData>()
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
        val itemBinding = DataBindingUtil.inflate<ItemMembersBinding>(
            inflater,
            R.layout.item_members, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size
//        return 5
    }

    fun getItem(p: Int): PersonDetailData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        try {

            holder.itemBinding.tvName.text = item.name
            holder.itemBinding.tvRelation.text = "સંબંધ : ${item.relation}"
            holder.itemBinding.tvPhone.text = item.phone
            holder.itemBinding.tvDOB.text = "જ. તા : ${item.dob}"
            holder.itemBinding.tvEducation.text = "અભ્યાસ : ${item.education}"
            holder.itemBinding.tvAge.text = "ઉંમર : ${item.age}"
            holder.itemBinding.tvBloodGroup.text = "બ્લડ ગ્રુપ : ${item.bloodgroup}"
            holder.itemBinding.tvBusiness.text = "બીઝનેસ : ${item.occupation}"

//            val urlLogo = item.image
//            Debug.e(item.image)
//            Utils.loadImage(holder.itemBinding.imageGallery, urlLogo!!,context, R.drawable.placeholder)
//            holder.itemBinding.textviewGallary.visibility = View.GONE

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        holder.itemBinding.root.setOnClickListener {
            mEventListener.onItemClick(position)
        }

    }

    fun addAll(mData: List<PersonDetailData>?) {
        data.clear()
        data.addAll(mData!!)
        notifyDataSetChanged()
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(internal var itemBinding: ItemMembersBinding) : RecyclerView.ViewHolder(itemBinding.root)
}