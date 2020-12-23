package com.newproject.apputils

import android.os.Handler

object ExitStrategy {

    private var isAbletoExit = false

    private val h = Handler()

    internal var runnable: Runnable = Runnable { isAbletoExit = false }

    fun canExit(): Boolean {
        return isAbletoExit
    }

    fun startExitDelay(delayMillis: Long) {
        isAbletoExit = true
        h.postDelayed(runnable, delayMillis)
    }

    fun shutDown() {
        isAbletoExit = false
        h.removeCallbacks(runnable)
    }

}
