package com.newproject.ui.gallery.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoAdapter
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoData
import com.newproject.ui.exoplayer.view.ExoplayerActivity
import com.newproject.ui.gallery.datamodel.GalleryData
import com.newproject.ui.gallery.utils.GalleryAdapter
import com.newproject.ui.homefragment.utils.HomeAdapter
import com.newproject.ui.homefragment.utils.HomeData

class GalleryViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityGalleryBinding
    private lateinit var mContext: Context
    lateinit var adapter: GalleryAdapter

    var dataList: MutableList<GalleryData> = mutableListOf()

    fun setBinder(binder: ActivityGalleryBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
        dataList.clear()
        dataList.add(GalleryData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "Hello"))
        dataList.add(GalleryData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "Hello"))
        dataList.add(GalleryData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png","Hello"))
        dataList.add(GalleryData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "Hello"))
        dataList.add(GalleryData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "Hello"))
        dataList.add(GalleryData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "Hello"))
        dataList.add(GalleryData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "Hello"))
        dataList.add(GalleryData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "Hello"))
        adapter = GalleryAdapter(mContext)
        adapter.addAll(dataList)
        binder.recyclerViewGallary.adapter = adapter
        adapter.setEventListener(object : GalleryAdapter.EventListener {
            override fun onItemClick(pos: Int, item: GalleryData) {

            }
        })

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