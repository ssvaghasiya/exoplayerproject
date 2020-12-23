package com.newproject.ui.mytask.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityMyTaskBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.home.datamodel.*
import com.newproject.ui.home.utils.PopUp
import com.newproject.ui.mytask.utils.MyTaskAdapter
import com.newproject.ui.taskdetail.taskdetaildesc.view.TaskDetailsDescriptionActivity
import com.google.gson.Gson
import java.util.*

class MyTaskViewModel(application: Application) : BaseViewModel(application),
    AdapterView.OnItemSelectedListener {

    private lateinit var binder: ActivityMyTaskBinding
    private lateinit var mContext: Context
    lateinit var myTaskAdapter: MyTaskAdapter
    lateinit var allVisitsDataModel: AllVisitsDataModel
    lateinit var searchVisitDataModel: SearchVisitDataModel
    var handler = Handler()



    fun setBinder(binder: ActivityMyTaskBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbar.topBarClickListener = SlideMenuClickListener()
        allVisitsDataModel = AllVisitsDataModel()
        searchVisitDataModel = SearchVisitDataModel()
        binder.topbar.isSettingShow = true
        binder.topbar.isCenterTextShow = true
        init()
    }

    private fun init() {
        initDrawer(mContext)
        myTaskAdapter = MyTaskAdapter(mContext)
        binder.rvTitleData1.adapter = myTaskAdapter
        myTaskAdapter.setEventListener(object : MyTaskAdapter.EventListener {
            override fun onItemClick(pos: Int, item: TodaysVisitData.Datum) {
                var intent = Intent(mContext, TaskDetailsDescriptionActivity::class.java)
                intent.putExtra("visitId", item.visitId)
                intent.putExtra("status", item.status)
                mContext.startActivity(intent)
            }
        })
        var adapter = ArrayAdapter.createFromResource(
            mContext,
            R.array.item_tasks,
            R.layout.style_spinner_home_layout
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_home_layout)
        binder.spinnerTasks.adapter = adapter
        binder.spinnerTasks.onItemSelectedListener = this
        selectPosition(0)
        binder.edtSearchVisit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    handler.removeCallbacks(searchRunnable)
                    handler.postDelayed(searchRunnable, 500)
                } else {
                    myTaskAdapter.clear()
                    getAllVisits()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
//                search(s.toString())
            }
        })
        getAllVisits()
        getEmployeeAttendance(mContext, object : CheckInBreakListener {
            override fun onAttendance(attendanceData: AttendanceData?) {
                callAttendance(attendanceData)
            }

            override fun onCheckedIn() {

            }

            override fun onCheckedOut() {

            }

            override fun onBreakStart() {

            }

            override fun onBreakEnd() {

            }

        })

    }

    val searchRunnable = Runnable { getSearchVisit(binder.edtSearchVisit.text.toString()) }


    fun onBackPressed() {
        if(popUpwindow != null){
            popUp.closedPopup(popUpwindow!!)
        }
    }

    private fun getAllVisits() {
        showDialog("", mContext as Activity)
        allVisitsDataModel.getAllVisits(mContext)
            .observeForever { allVisitsData ->
                dismissDialog()
                onCallResult(allVisitsData)
            }
    }

    private fun onCallResult(allVisitsData: AllVisitsData?) = try {
        if (allVisitsData != null) {
            when (allVisitsData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    myTaskAdapter.addAll(allVisitsData.data!!)
                    Utils.setPref(
                        mContext,
                        Constant.ALL_VISITS_DATA,
                        Gson().toJson(allVisitsData)
                    )
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail To Get Data")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast("Fail To Get Data")
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getSearchVisit(search: String) {
        searchVisitDataModel.search = search
        searchVisitDataModel.getSearchVisit(mContext)
            .observeForever { searchVisitData ->
                onCallResult(searchVisitData)
            }
    }

    private fun onCallResult(searchVisitData: SearchVisitData?) = try {
        if (searchVisitData != null) {
            when (searchVisitData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    myTaskAdapter.clear()
                    myTaskAdapter.addAll(searchVisitData.data!!.visits!!)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast(searchVisitData.errors!!.search!!)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(searchVisitData.errors!!.search!!)
                }
            }
        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        selectPosition(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


    private fun selectPosition(position: Int) {
        if (position == 0) {
        }
    }

    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
            if (value.equals(getLabelText(R.string.menu))) {
                try {
                    onTopMenuClick()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (value.equals(getLabelText(R.string.toggle))) {
                var statusOfBreakSwitch = employeeAttendance.breakStatus == 1
                Debug.e("Break", statusOfBreakSwitch.toString())
                try {

                    if (!statusOfBreakSwitch) {
                        if (employeeAttendance.checkIn == 1) {
                            getBreakStart(mContext, object : CheckInBreakListener {
                                override fun onAttendance(attendanceData: AttendanceData?) {
                                    callAttendance(attendanceData)
                                }

                                override fun onCheckedIn() {

                                }

                                override fun onCheckedOut() {

                                }

                                override fun onBreakStart() {

                                }

                                override fun onBreakEnd() {

                                }

                            })
                        }
                    } else if (statusOfBreakSwitch) {
                        getBreakEnd(mContext, object : CheckInBreakListener {
                            override fun onAttendance(attendanceData: AttendanceData?) {
                                callAttendance(attendanceData)
                            }

                            override fun onCheckedIn() {

                            }

                            override fun onCheckedOut() {

                            }

                            override fun onBreakStart() {

                            }

                            override fun onBreakEnd() {

                            }

                        })
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (value.equals(getLabelText(R.string.toggle_check_in_out))) {
                var statusOfSwitch = employeeAttendance.checkIn == 1
                Debug.e("CheckIn", statusOfSwitch.toString())
                try {
                    if (!statusOfSwitch) {
                        getCheckIn(mContext, object : CheckInBreakListener {
                            override fun onAttendance(attendanceData: AttendanceData?) {
                                callAttendance(attendanceData)
                            }

                            override fun onCheckedIn() {

                            }

                            override fun onCheckedOut() {

                            }

                            override fun onBreakStart() {

                            }

                            override fun onBreakEnd() {

                            }

                        })
                    } else {
                        getCheckout(mContext, object : CheckInBreakListener {
                            override fun onAttendance(attendanceData: AttendanceData?) {
                                callAttendance(attendanceData)
                            }

                            override fun onCheckedIn() {

                            }

                            override fun onCheckedOut() {

                            }

                            override fun onBreakStart() {

                            }

                            override fun onBreakEnd() {

                            }

                        })
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }

        override fun onBackClicked(view: View?) {
            (mContext as Activity).finish()
        }
    }

    fun callAttendance(attendanceData: AttendanceData?) {
        employeeAttendance = attendanceData?.data?.employeeAttendance!!
        Utils.setPref(mContext, Constant.ATTENDANCE_DATA, Gson().toJson(attendanceData))

        if (attendanceData != null) {
            employeeAttendance = attendanceData.data?.employeeAttendance!!

//            binder.topbar.switchChecKInOut.isChecked =
//                attendanceData.data?.employeeAttendance?.checkIn == 1
//            binder.topbar.switchBreakStartEnd.isChecked =
//                attendanceData.data?.employeeAttendance?.breakStatus == 1
//
//            binder.topbar.switchBreakStartEnd.isEnabled = !(attendanceData.data?.employeeAttendance?.checkIn == 0 && attendanceData.data?.employeeAttendance?.breakStatus == 0)


            if (attendanceData.data?.employeeAttendance?.breakStatus == 1) {

                val bHour =
                    attendanceData.data!!.employeeAttendance!!.breakStartTime!!.split(":")[0]
                val bMin =
                    attendanceData.data!!.employeeAttendance!!.breakStartTime!!.split(":")[1]
                val bSec =
                    attendanceData.data!!.employeeAttendance!!.breakStartTime!!.split(":")[2]

                Debug.e("bHourminsec", bHour + " " + bMin + " " + bSec)

                bCal = Utils.getUTCCalendar()
                bCal?.set(Calendar.HOUR_OF_DAY, bHour.toInt())
                bCal?.set(Calendar.MINUTE, bMin.toInt())
                bCal?.set(Calendar.SECOND, bSec.toInt())
                bCal?.set(Calendar.MILLISECOND, 0)

                popUp.bCal = bCal

                Debug.e("bCal", bCal.toString())

                popUpwindow =
                    popUp.showPopupWindow(
                        binder.topbar.imgAddFriend,
                        object : PopUp.PopupDismissListener {
                            override fun onDismiss() {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    binder.llMain.foreground =
                                        ColorDrawable(Color.TRANSPARENT)
                                }
                            }
                        })

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    binder.llMain.foreground =
                        ColorDrawable(mContext.getColor(R.color.black_trans))
                }
            }
        }
    }

    inner class ViewClickHandler {

    }

}
