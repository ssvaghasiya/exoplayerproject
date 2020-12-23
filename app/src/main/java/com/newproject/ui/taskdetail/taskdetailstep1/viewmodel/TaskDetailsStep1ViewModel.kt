package com.newproject.ui.taskdetail.taskdetailstep1.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.DatePicker
import android.widget.PopupWindow
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityTaskDetailsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDetails
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisitModel
import com.newproject.ui.taskdetail.taskdetailstep1.utils.PopUpStartVisit
import com.newproject.ui.taskdetail.taskdetailstep2.view.TaskDetailStep2Activity
import kotlinx.android.synthetic.main.top_bar_back_toggle.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsStep1ViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityTaskDetailsBinding
    private lateinit var mContext: Context
    lateinit var popUpWindow: PopupWindow
    lateinit var startVisitModel: StartVisitModel
    var visitData: TaskDetails.Visit? = null
    var sample: Int? = 1
    var visitId: Int? = 0
    var visit_status: String? = null
    var cal = Calendar.getInstance()
    var date: String? = null

    fun setBinder(binder: ActivityTaskDetailsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbarback.topBarClickListener = SlideMenuClickListener()
        binder.topbarback.isSettingShow = true
        binder.topbarback.isCenterTextShow = true
        binder.topbarback.txtCenterText.text = mContext.getString(R.string.task_details)
        binder.topbarback.switchStartVisit.isEnabled = false
        startVisitModel = StartVisitModel()
        init()
    }

    private fun init() {
        visitData =
            (mContext as Activity).intent.getSerializableExtra("visitData") as TaskDetails.Visit?
        visitId = visitData?.visitId
        visit_status = visitData?.status
        setData()
    }

    fun setData() {
        binder.txtCompanyName.setText(visitData?.companyName)
        binder.txtPersonName.setText(visitData?.leadName)
        binder.txtArea.setText(visitData?.areaName)
        binder.txtCity.setText(visitData?.city)
        binder.txtDateTime.text = parseDate(visitData?.visitDate, "MMM dd, yyyy 'at' h:mm a")
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
            if (visit_status?.equals(mContext.getString(R.string.pending))!!) {
                if (value.equals(getLabelText(R.string.switch_start_visit))) {
                    var popUp = PopUpStartVisit()
                    try {
                        if (view.switchStartVisit.isChecked) {
                            popUpWindow =
                                popUp.showPopupWindow(
                                    view,
                                    object : PopUpStartVisit.PopupDismissListener {
                                        override fun onDismiss() {
                                            view.switchStartVisit.isChecked = false
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                binder.llTaskStep1.foreground =
                                                    ColorDrawable(Color.TRANSPARENT)
                                            }
                                        }
                                    },
                                    mContext,
                                    visitId
                                )
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                binder.llTaskStep1.foreground =
                                    ColorDrawable(mContext.getColor(R.color.black_trans))
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else if (visit_status.equals(mContext.getString(R.string.completed))) {
                binder.topbarback.switchStartVisit.isChecked = false
                binder.topbarback.switchStartVisit.isEnabled = false
            }
        }

        override fun onBackClicked(view: View?) {
            (mContext as Activity).finish()
        }
    }


    inner class ViewClickHandler {
        fun onNextStep(view: View) {
            try {
                if (isValidate()) {

                    var intent = Intent(mContext, TaskDetailStep2Activity::class.java)
                    intent.putExtra("step1data", visitData)
                    intent.putExtra("topic", binder.txtTopicOfVisit.text.toString())
                    intent.putExtra("sample", sample)
                    intent.putExtra("date", date)
                    mContext.startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onPreviousClick(view: View) {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onYes(view: View) {
            try {
                sample = 1
                binder.btnYes.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape8));
                binder.btnNo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape9));
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onNo(view: View) {
            try {
                binder.btnNo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape8));
                binder.btnYes.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape9));
                sample = 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                TimePickerDialog(
                    mContext,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
        }

        val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                updateDateInView()
            }
        }

        fun onDatePicker(view: View) {
            Debug.e("Click Date Picker")
            try {
                DatePickerDialog(
                    mContext,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDateInView() {
        val myFormat = "MMM dd, yyyy 'at' h:mm a" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        binder.txtDateTime.text = sdf.format(cal.time).toString()
        date = cal.time.toString()
    }

    fun isValidate(): Boolean {
        if (binder.txtTopicOfVisit.text.isNullOrEmpty()) {
            showToast(mContext.getString(R.string.topic_field_is_require))
            return false
        }
        return true
    }
}