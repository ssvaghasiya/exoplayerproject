package com.newproject.ui.temp.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityTempBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.temp.datamodel.TempData
import com.newproject.ui.temp.utils.TempAdapter

class TempViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityTempBinding
    private lateinit var mContext: Context
    lateinit var adapter: TempAdapter

    var dataList: MutableList<TempData> = mutableListOf()

    fun setBinder(binder: ActivityTempBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
        dataList.clear()
        dataList.add(TempData( "name1"))
        dataList.add(TempData( "name2"))
        dataList.add(TempData( "name3"))
        dataList.add(TempData( "name4"))
        dataList.add(TempData( "name5"))
        dataList.add(TempData( "name6"))
        dataList.add(TempData( "name7"))
        dataList.add(TempData( "name8"))
        dataList.add(TempData( "name9"))
        dataList.add(TempData( "name10"))
        dataList.add(TempData( "name11"))
        dataList.add(TempData( "name12"))
        dataList.add(TempData( "name13"))
        dataList.add(TempData( "name14"))
        dataList.add(TempData( "name15"))
        dataList.add(TempData( "name1"))
        dataList.add(TempData( "name2"))
        dataList.add(TempData( "name3"))
        dataList.add(TempData( "name4"))
        dataList.add(TempData( "name5"))
        dataList.add(TempData( "name6"))
        dataList.add(TempData( "name7"))
        dataList.add(TempData( "name8"))
        dataList.add(TempData( "name9"))
        dataList.add(TempData( "name10"))
        dataList.add(TempData( "name11"))
        dataList.add(TempData( "name12"))
        dataList.add(TempData( "name13"))
        dataList.add(TempData( "name14"))
        dataList.add(TempData( "name15"))


        adapter = TempAdapter(mContext)
        binder.rvViewAddSports.adapter = adapter
        adapter.addAll(dataList)
        adapter.setEventListener(object : TempAdapter.EventListener {
            override fun onItemClick(pos: Int) {

            }

            override fun onGetSelected(size: Int?) {
                Debug.e("counts",size.toString())

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