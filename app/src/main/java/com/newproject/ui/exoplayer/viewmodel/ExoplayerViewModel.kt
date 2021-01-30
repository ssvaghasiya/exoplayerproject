package com.newproject.ui.exoplayer.viewmodel

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.newproject.R
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityExoplayerBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.home.view.HomeActivity

class ExoplayerViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityExoplayerBinding
    lateinit var mContext: Context

    var simpleExoPlayer: SimpleExoPlayer? = null
    var mExoPlayerFullscreen = false
    var mute = false
    var currentvolume: Float = 0f
    var isVideo = false
    lateinit var bt_fullscreen: ImageView
    lateinit var bt_mute: ImageView
    private var mFullScreenDialog: Dialog? = null
    lateinit var videoUrl: String

    fun setBinder(
        binder: ActivityExoplayerBinding
    ) {
        this.binder = binder
        this.mContext = binder.root.context
        binder.viewModel = this
        binder.viewClickHandler = ViewClickHandler()
        binder.topBar.isTextShow = true
        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }


    fun init() {
        bt_fullscreen = (mContext as Activity).findViewById(R.id.bt_fullscreen)
        bt_mute = (mContext as Activity).findViewById(R.id.bt_mute)
        videoUrl = (mContext as Activity).intent.getStringExtra("videoUrl")!!
        setupVideoPlayer()
    }


    fun setupVideoPlayer() {
        try {
            isVideo = true
            binder.ivDetailPlayVideo.visibility = View.GONE
            binder.playerView.visibility = View.VISIBLE
//            val videoUrl =
//                Uri.parse("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4")
            val videoUrl = Uri.parse(videoUrl)
            val loadControl: LoadControl = DefaultLoadControl()
            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
            val trackSelector: TrackSelector =
                DefaultTrackSelector(AdaptiveTrackSelection.Factory())
            simpleExoPlayer =
                ExoPlayerFactory.newSimpleInstance(
                    mContext,
                    trackSelector,
                    loadControl
                )
            val factory = DefaultHttpDataSourceFactory("exoplayer_video")
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
            var mediaSource: MediaSource =
                ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null)
            binder.playerView.player = simpleExoPlayer
            binder.playerView.keepScreenOn = true
            simpleExoPlayer?.prepare(mediaSource, true, true)
            simpleExoPlayer?.playWhenReady = true
            currentvolume = simpleExoPlayer!!.volume

            simpleExoPlayer?.addListener(object : Player.EventListener {

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_BUFFERING) {
                        binder.progressBar.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_READY) {
                        binder.progressBar.visibility = View.GONE
                    }
                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray,
                    trackSelections: TrackSelectionArray
                ) {
                    Log.e("CurrentIndex", simpleExoPlayer!!.currentWindowIndex.toString())
                }

                override fun onPlayerError(error: ExoPlaybackException) {
                    simpleExoPlayer!!.stop()
                }
            })

            bt_fullscreen.setOnClickListener(View.OnClickListener {

                if (mExoPlayerFullscreen) {
                    (mContext as Activity).requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    closeFullscreenDialog()
                } else {
                    (mContext as Activity).requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    openFullscreenDialog()
                }
            })

            bt_mute.setOnClickListener(View.OnClickListener {
                if (mute) {
                    simpleExoPlayer!!.volume = currentvolume
                    mute = false
                    bt_mute.setImageDrawable(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.ic_baseline_volume_off_24
                        )
                    )
                } else {
                    simpleExoPlayer!!.volume = 0f
                    mute = true
                    bt_mute.setImageDrawable(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.ic_baseline_volume_up_24
                        )
                    )
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onConfigurationChanged(newConfig: Configuration) {

        try {
            try {//Checks the orientation of the screen
                if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
                    closeFullscreenDialog()
                } else if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
                    if (isVideo) {
                        openFullscreenDialog()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mFullScreenDialog!!.cancel()
                mFullScreenDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initFullscreenDialog() {
        mFullScreenDialog =
            object : AppCompatDialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
                override fun onBackPressed() {
                    if (mExoPlayerFullscreen)
                        closeFullscreenDialog()
                    super.onBackPressed()
                }
            }
    }

    private fun openFullscreenDialog() {
        (binder.playerView.parent as ViewGroup).removeView(binder.playerView)
        mFullScreenDialog!!.addContentView(
            binder.playerView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        bt_fullscreen.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_baseline_fullscreen_exit_24
            )
        )
        mExoPlayerFullscreen = true
        mFullScreenDialog!!.show()
    }


    private fun closeFullscreenDialog() {
        (binder.playerView.parent as ViewGroup).removeView(binder.playerView)
        (binder.FLVideoViewMain as FrameLayout).addView(binder.playerView)
        mExoPlayerFullscreen = false
        mFullScreenDialog!!.dismiss()
        mFullScreenDialog!!.setCancelable(true)
        bt_fullscreen.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_baseline_fullscreen_24
            )
        )
        (mContext as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    fun onResume() {
        initFullscreenDialog()
        if (simpleExoPlayer != null) {
            simpleExoPlayer!!.playWhenReady = false
            simpleExoPlayer!!.playbackState
        }

        if (mExoPlayerFullscreen) {
            (binder.playerView.getParent() as ViewGroup).removeView(binder.playerView)
            mFullScreenDialog!!.addContentView(
                binder.playerView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            bt_fullscreen.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.ic_baseline_fullscreen_exit_24
                )
            )
            mFullScreenDialog!!.show()
        }
    }

    fun onDestroy() {
        try {
            if (simpleExoPlayer != null) {
                simpleExoPlayer?.playWhenReady = false
                simpleExoPlayer!!.playbackState
                simpleExoPlayer!!.stop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onPause() {
        try {
            if (simpleExoPlayer != null) {
                simpleExoPlayer!!.playWhenReady = false
                simpleExoPlayer!!.playbackState
            }
            if (mFullScreenDialog != null) mFullScreenDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onRestart() {
        try {
            if (simpleExoPlayer != null) {
                simpleExoPlayer!!.playWhenReady = true
                simpleExoPlayer!!.playbackState
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class ViewClickHandler {

        fun onPlay(view: View) {
            try {
                setupVideoPlayer()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onClick(view: View) {
            try {
                var intent = Intent(mContext, HomeActivity::class.java)
                mContext.startActivity(intent)
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

