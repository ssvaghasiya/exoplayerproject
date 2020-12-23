package com.newproject.ui.taskdetail.taskdetaildesc.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.custom.CButtonView
import com.newproject.databinding.ActivityTaskDetailsDescriptionBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDataModel
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDetails
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisit
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisitModel
import com.newproject.ui.taskdetail.taskdetailstep1.view.TaskDetailsStep1Activity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsDescriptionViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityTaskDetailsDescriptionBinding
    private lateinit var mContext: Context
    lateinit var taskDataModel: TaskDataModel
    lateinit var startVisitModel: StartVisitModel
    var alertDialog: AlertDialog? = null
    var visits: TaskDetails.Visit? = null
    var visitId: Int? = 0
    var status: String? = null

    fun setBinder(binder: ActivityTaskDetailsDescriptionBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbarback.topBarClickListener = SlideMenuClickListener()
        binder.topbarback.isSettingShow = true
        binder.topbarback.isCenterTextShow = true
        binder.topbarback.txtCenterText.text = mContext.getString(R.string.task_details)
        startVisitModel = StartVisitModel()
        taskDataModel = TaskDataModel()
        init()
    }

    private fun init() {
//        visitData =
//            (mContext as Activity).intent.getSerializableExtra("visit") as? AllVisitsData.Data.Visit
        visitId = (mContext as Activity).intent.getIntExtra("visitId", 0)
        status = (mContext as Activity).intent.getStringExtra("status")
        Debug.e("visitid",visitId.toString())
        getVisitDetail(visitId)
    }


    private fun getVisitDetail(id: Int?) {

        taskDataModel.visitId = id.toString()
        showDialog("", mContext as Activity)
        taskDataModel.getTask(mContext).observeForever { taskDetails ->
            dismissDialog()
            onCallResult(taskDetails)
        }
    }

    private fun onCallResult(taskDetails: TaskDetails?) = try {
        if (taskDetails != null) {
            when (taskDetails.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    Utils.setPref(
                        mContext,
                        Constant.VISIT_DETAILS,
                        Gson().toJson(taskDetails)
                    )
                    this.visits = taskDetails.data?.visits
                    var visitDateTime =
                        parseDate(visits?.visitStart, "MMM dd, yyyy 'at' h:mm a")
                    var createdDate =
                        getDate(visits?.createdDate)
                    binder.txtCompanyName.text = visits?.companyName
                    binder.txtArea.text = visits?.areaName
                    binder.txtCity.text = visits?.city
                    binder.txtPersonName.text = visits?.leadName
                    binder.txtDateTime.text = visitDateTime
                    binder.txtCreatedBy.text = visits?.createdBy
                    binder.txtCreatedDate.text = createdDate
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast(taskDetails.message)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(taskDetails.message)
                }
            }
        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    @SuppressLint("SimpleDateFormat")
     fun getDate(time: String?): String? {
        val inputPattern = "yyyy-MM-dd"
        val outputPattern = "MMM dd, yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)

            if (value.equals(getLabelText(R.string.back))) {
                try {
                    onBackClicked(view)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        override fun onBackClicked(view: View?) {
            (mContext as Activity).finish()
        }
    }

    fun startVisit(visitId: Int?) {
        startVisitModel.visitId = visitId.toString()
        startVisitModel.latitude = "234123"
        startVisitModel.longitude = "2133"
        showDialog("", mContext as Activity)
        startVisitModel.startVisit(mContext!!)
            .observeForever { startVisit ->
                dismissDialog()
                onCallResult(startVisit)
            }
    }

    private fun onCallResult(startVisit: StartVisit?) = try {
        if (startVisit != null) {
            when (startVisit.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    alertDialog?.dismiss()
                    var intent = Intent(mContext, TaskDetailsStep1Activity::class.java)
                    intent.putExtra("visitData", visits)
                    mContext.startActivity(intent)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    inner class ViewClickHandler {

        fun onStartProcess(view: View) {
            try {
                showCustomDialog()
//                var intent = Intent(mContext, TaskDetailsStep1Activity::class.java)
//                intent.putExtra("visitData", visits?.get(0))
//                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onCancle(view: View) {
            try {
                finishActivity(mContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showCustomDialog() {

        val dialogView: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_start_visit, null, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setView(dialogView)
        alertDialog = builder.create()

        var yes = dialogView.findViewById<CButtonView>(R.id.buttonYes)

        yes.setOnClickListener {
            if(status == mContext.getString(R.string.complete)){
                showToast("This visit is completed")
            } else{
                startVisit(visitId)
            }
        }

        var no = dialogView.findViewById<CButtonView>(R.id.buttonNo)
        no.setOnClickListener {
            alertDialog?.dismiss()
        }
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }
}
