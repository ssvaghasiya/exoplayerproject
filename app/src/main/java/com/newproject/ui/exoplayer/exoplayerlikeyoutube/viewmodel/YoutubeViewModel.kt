package com.newproject.ui.exoplayer.exoplayerlikeyoutube.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.exoplayer2.*
import com.newproject.R
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityYoutubeBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoAdapter
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoData
import com.newproject.ui.exoplayer.view.ExoplayerActivity

class YoutubeViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityYoutubeBinding
    lateinit var mContext: Context
    lateinit var adapter: VideoAdapter
    var dataList: MutableList<VideoData> = mutableListOf()

    fun setBinder(
        binder: ActivityYoutubeBinding
    ) {
        this.binder = binder
        this.mContext = binder.root.context
        binder.viewModel = this
        binder.viewClickHandler = ViewClickHandler()
        binder.topbar.isTextShow = true
        binder.topbar.topBarClickListener = SlideMenuClickListener()
        init()
    }


    fun init() {
        dataList.clear()
        dataList.add(VideoData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        dataList.add(VideoData("https://peach.blender.org/wp-content/uploads/bbb-splash.png", "https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4"))
        adapter = VideoAdapter(mContext)
        adapter.addAll(dataList)
        binder.rvVideos.adapter = adapter
        adapter.setEventListener(object : VideoAdapter.EventListener {
            override fun onItemClick(pos: Int, item: VideoData) {
                var intent = Intent(mContext, ExoplayerActivity::class.java)
                intent.putExtra("videoUrl",item.url)
                mContext.startActivity(intent)
            }
        })
    }

    inner class ViewClickHandler {

        fun onPlay(view: View) {
            try {
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onClick(view: View) {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onClickTask(view: View) {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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
}

