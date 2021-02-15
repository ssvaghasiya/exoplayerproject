package com.newproject.ui.addmember.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityAddMemberBinding
import com.newproject.databinding.ActivityAddPersonBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.surname.datamodel.SurnameData

class AddMemberViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityAddMemberBinding
    private lateinit var mContext: Context

    fun setBinder(binder: ActivityAddMemberBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
//        binder.topBar.isTextShow = true
//        binder.topBar.isBackShow = true
//        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }

    private fun init() {

    }

    private fun getSurnameList() {
        try {
            showDialog("",mContext as Activity)
            var query = db!!.collection(FirestoreTable.SURNAME)


            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(SurnameData::class.java)
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