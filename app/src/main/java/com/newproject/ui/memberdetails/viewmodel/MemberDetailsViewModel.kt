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

class MemberDetailsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityMemberDetailsBinding
    private lateinit var mContext: Context
    lateinit var adapter: AdvertiseAdapter


    fun setBinder(binder: ActivityMemberDetailsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
//        adapter = AdvertiseAdapter(mContext)
//        binder.rvAds.adapter = adapter
//        adapter.setEventListener(object : AdvertiseAdapter.EventListener {
//            override fun onItemClick(pos: Int, item: GalleryData) {
//
//            }
//        })
//        getAdsList()
    }

    private fun getAdsList() {
        try {

            showDialog("", mContext as Activity)
            var query = db!!.collection(FirestoreTable.BUSINESSSUBADVERTISE)

            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(GalleryData::class.java)
                    adapter.addAll(item)
                    Debug.e("Get All Data Successfully")
                }
                dismissDialog()
            }.addOnFailureListener {
                it.printStackTrace()
                dismissDialog()
            }.addOnCompleteListener {
                dismissDialog()
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