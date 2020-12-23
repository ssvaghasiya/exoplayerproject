package com.newproject.ui.taskdetail.taskdetailstep1.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisit
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisitModel
import com.newproject.ui.taskdetail.taskdetailstep2.view.TaskDetailStep2Activity
import kotlinx.android.synthetic.main.item_start_visit.view.*
import kotlinx.android.synthetic.main.top_bar_back_toggle.view.*

class PopUpStartVisit {
    lateinit var inflater: LayoutInflater
    lateinit var popUpView: View
    lateinit var popUpWindow: PopupWindow
    lateinit var startVisitModel: StartVisitModel
    var mContext: Context? = null

    fun showPopupWindow(
        view: View,
        listener: PopupDismissListener,
        context: Context,
        visitId: Int?
    ): PopupWindow {
        this.mContext = context
        startVisitModel = StartVisitModel()
        inflater = LayoutInflater.from(view.context)
        popUpView = inflater.inflate(R.layout.item_start_visit, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        popUpWindow = PopupWindow(popUpView, width, height)
        popUpWindow.isOutsideTouchable = false
        popUpWindow.isFocusable = true

        popUpWindow.setOnDismissListener {
            try {
                listener.onDismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        popUpWindow.showAsDropDown(view.switchStartVisit, 0, 60)
        popUpView.buttonYes.setOnClickListener {
//
            startVisit(visitId)
        }
        popUpView.buttonNo.setOnClickListener {
            popUpWindow.dismiss()
        }
        return popUpWindow
    }

    interface PopupDismissListener {
        fun onDismiss()
    }

    fun startVisit(visitId: Int?) {
        startVisitModel.visitId = visitId.toString()
        startVisitModel.latitude = "234123"
        startVisitModel.longitude = "2133"
//        (mContext as AppViewModel).showDialog("", mContext as Activity)
        startVisitModel.startVisit(mContext!!)
            .observeForever { startVisit ->
//                (mContext as AppViewModel).dismissDialog()
                onCallResult(startVisit)
            }
    }

    private fun onCallResult(startVisit: StartVisit?) = try {
        if (startVisit != null) {
            when (startVisit.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    popUpWindow.dismiss()
                    Toast.makeText(mContext, startVisit.data?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(mContext, TaskDetailStep2Activity::class.java)
                    mContext?.startActivity(intent)
                    (mContext as Activity).finish()
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
//                        Toast.makeText(mContext, startVisit.data_msg, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
//                    Toast.makeText(mContext, startVisit.data_msg, Toast.LENGTH_SHORT).show()
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}