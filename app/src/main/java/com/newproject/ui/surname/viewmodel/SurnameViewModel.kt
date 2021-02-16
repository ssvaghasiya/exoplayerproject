package com.newproject.ui.surname.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.firebase.firestore.Query
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.exoplayer.view.ExoplayerActivity
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surname.utils.SurnameAdapter
import com.newproject.ui.surnamecontacts.view.SurnameContactsActivity

class SurnameViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameAdapter


    fun setBinder(binder: ActivitySurnameBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        binder.topBar.isTextShow = true
        binder.topBar.isBackShow = true
        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }

    private fun init() {
        adapter = SurnameAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.setEventListener(object : SurnameAdapter.EventListener {
            override fun onItemClick(pos: Int, item: SurnameData) {
                var intent = Intent(mContext, SurnameContactsActivity::class.java)
                intent.putExtra("surname",item)
                mContext.startActivity(intent)
            }
        })
        getSurnameList()
    }

    private fun getSurnameList() {
        try {

//            var query = db.collection(FirestoreTable.CHAT)
//                .whereEqualTo(RequestParamsUtils.SENDER_ID, loggedInUserId)
            showDialog("",mContext as Activity)
            var query = db!!.collection(FirestoreTable.SURNAME).orderBy("surname", Query.Direction.ASCENDING)


            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(SurnameData::class.java)
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