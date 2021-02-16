package com.newproject.ui.memberdetails.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityAdsBinding
import com.newproject.databinding.ActivityMemberDetailsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.ads.utils.AdvertiseAdapter
import com.newproject.ui.gallery.datamodel.GalleryData
import com.newproject.ui.memberdetails.utils.MembersAdapter
import com.newproject.ui.persondetail.datamodel.PersonDetailData
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData

class MemberDetailsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityMemberDetailsBinding
    private lateinit var mContext: Context
    lateinit var adapter: MembersAdapter
    var person: SurnameContactsData? = null


    fun setBinder(binder: ActivityMemberDetailsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
        person = (mContext as Activity).intent.extras?.getSerializable("person") as SurnameContactsData
        if(person != null) {
            binder.tvpersonName.text = person?.name.toString()
            binder.tvAddress.text = person?.address.toString()
            binder.tvPhone.text = person?.phone.toString()
            binder.tvBusiness.text = person?.business_detail.toString()
            binder.tvBusinessAddress.text = person?.business_address.toString()
        }

        adapter = MembersAdapter(mContext)
        binder.rvMembers.adapter = adapter
        adapter.setEventListener(object : MembersAdapter.EventListener {
            override fun onItemClick(pos: Int) {

            }
        })
        getPersonDetails()
    }
    private fun getPersonDetails() {
        try {

            showDialog("", mContext as Activity)
            db!!.collection(FirestoreTable.SUB_DETAIL_EACH_MEMBER)
                .whereEqualTo("main_number", person!!.phone!!.trim()).get()
                .addOnSuccessListener { documents ->
                    dismissDialog()
                    Debug.e("documents", documents.size().toString())
                    val item = documents.toObjects(PersonDetailData::class.java)
                    adapter.addAll(item)
                }
                .addOnFailureListener { exception ->
                    dismissDialog()
                    Debug.e("Error getting documents:", exception.message.toString())
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
            if (value.equals(getLabelText(R.string.menu))) {
                try {

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onBackClicked(view: View?) {
        }
    }

    inner class ViewClickHandler {
        fun onReviewsAndRanks(view: View) {
            try {
//                var intent = Intent(mContext, ReviewsAndRankActivity::class.java)
//                mContext.startActivity(intent)
//                (mContext as Activity).finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onSeeAll(view: View) {
            try {
//                var intent = Intent(mContext, ReviewsAndRankActivity::class.java)
//                mContext.startActivity(intent)
//                (mContext as Activity).finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}