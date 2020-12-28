package com.newproject.ui.surnamecontacts.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.databinding.ActivitySurnameContactsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surname.utils.SurnameAdapter
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import com.newproject.ui.surnamecontacts.utils.SurnameContactsAdapter

class SurnameContactsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameContactsBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameContactsAdapter

    var dataList: MutableList<SurnameContactsData> = mutableListOf()

    fun setBinder(binder: ActivitySurnameContactsBinding) {
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
        dataList.add(SurnameContactsData( 1,"name","7894561235"))
        dataList.add(SurnameContactsData( 2,"name","7894561235"))
        dataList.add(SurnameContactsData( 3,"name","7894561235"))
        dataList.add(SurnameContactsData( 4,"name","7894561235"))
        dataList.add(SurnameContactsData( 5,"name","7894561235"))
        dataList.add(SurnameContactsData( 6,"name","7894561235"))
        dataList.add(SurnameContactsData( 7,"name","7894561235"))

        adapter = SurnameContactsAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.addAll(dataList)
        adapter.setEventListener(object : SurnameContactsAdapter.EventListener {
            override fun onItemClick(pos: Int, item: SurnameContactsData) {

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