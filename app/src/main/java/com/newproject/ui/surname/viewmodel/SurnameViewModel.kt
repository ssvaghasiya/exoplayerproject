package com.newproject.ui.surname.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.exoplayer.view.ExoplayerActivity
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surname.utils.SurnameAdapter

class SurnameViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameAdapter

    var dataList: MutableList<SurnameData> = mutableListOf()

    fun setBinder(binder: ActivitySurnameBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        binder.topBar.isTextShow = true
        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }

    private fun init() {
        dataList.clear()
        dataList.add(SurnameData( "Hello"))
        dataList.add(SurnameData( "Hello"))
        dataList.add(SurnameData("Hello"))
        adapter = SurnameAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.addAll(dataList)
        adapter.setEventListener(object : SurnameAdapter.EventListener {
            override fun onItemClick(pos: Int, item: SurnameData) {
               
            }
        })

    }

    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
//            if (value.equals(getLabelText(R.string.menu))) {
//                try {
//
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
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