package com.newproject.ui.home.utils

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.lunch_break_dialogue_layout.view.*
import kotlinx.android.synthetic.main.topbar.view.*
import java.util.*


class PopUp {

    lateinit var inflater: LayoutInflater
    lateinit var popUpView: View
    lateinit var popUpWindow: PopupWindow
    var now: Calendar? = null
    var bCal: Calendar? = null
    var timer: CountDownTimer? = null

    fun showPopupWindow(view: View, listener: PopupDismissListener): PopupWindow {

        inflater = LayoutInflater.from(view.context)
        popUpView = inflater.inflate(R.layout.lunch_break_dialogue_layout, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        popUpWindow = PopupWindow(popUpView, width, height)


        popUpWindow.setOnDismissListener {
            try {
                cancelTimer()
                listener.onDismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        now = Utils.getUTCCalendar()

        popUpWindow.showAsDropDown(view.imgAddFriend, 0, 60)
        popUpWindow.isOutsideTouchable = false
        popUpWindow.isFocusable = true
        Debug.e("nowbCal",now.toString()+" "+now!!.timeInMillis+" "+bCal!!.timeInMillis)
        startTimer(now!!.timeInMillis - bCal!!.timeInMillis)
        return popUpWindow
    }

    interface PopupDismissListener {
        fun onDismiss()
    }

    fun closedPopup(window: PopupWindow) {
        cancelTimer()
        window.dismiss()
    }

    private fun startTimer(millis: Long) {
        Debug.e("millis",millis.toString())
        cancelTimer()
        timer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val doneMillis = (millis - millisUntilFinished) + millis
                Debug.e("millisuntil",millis.toString()+"  "+millisUntilFinished.toString())

                val hour = ((doneMillis / 1000) / 3600).toString().padStart(2, '0')
                val min = ((doneMillis / 1000 / 60) % 60).toString().padStart(2, '0')
//                val sec = ((doneMillis / 1000) % 60).toString().padStart(2, '0')

                Debug.e("cdTimer",  hour + ":" + min)
                popUpView.txtCountDownTimer.text = hour + ":" + min
            }

            override fun onFinish() {
                Debug.e("onTimer ", "finished")
            }
        }
        timer?.start()
    }

    fun cancelTimer() {
        try {
            if (timer != null) {
                timer?.cancel()
                timer == null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}