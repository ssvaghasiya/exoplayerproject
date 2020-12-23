package com.newproject.ui

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(foregroundBackgroundListener)
    }

    internal var foregroundBackgroundListener = ForegroundBackgroundListener()

    internal inner class ForegroundBackgroundListener : LifecycleObserver {

        var handler = Handler()

        var runGoToBackground: Runnable = Runnable {
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(Constant.APP_GOES_BACKGROUND))
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun startSomething() {
            Debug.e("ProcessLog", "APP IS ON FOREGROUND")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stopSomething() {
            handler.removeCallbacks(runGoToBackground)
            handler.postDelayed(runGoToBackground, 100)
            Debug.e("ProcessLog", "APP IS ON BACKGROUND")
        }
    }

}