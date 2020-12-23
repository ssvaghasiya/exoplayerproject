package com.newproject.ui.clients.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityClientsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.clients.datamodel.*
import com.newproject.ui.clients.utils.ClientsDataAdapter
import com.newproject.ui.clients.utils.HistoryDataAdapter
import com.newproject.ui.createnewtask.view.CreateNewTaskActivity
import com.newproject.ui.home.datamodel.*
import com.newproject.ui.home.utils.PopUp
import com.newproject.ui.taskdetail.taskdetaildesc.view.TaskDetailsDescriptionActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.alert_success_layout.view.*
import java.util.*

class ClientsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityClientsBinding
    private lateinit var mContext: Context
    lateinit var clientsDataAdapter: ClientsDataAdapter
    lateinit var historyDataAdapter: HistoryDataAdapter
    lateinit var clientDataModel: ClientDataModel
    lateinit var visitHistoryDataModel: VisitHistoryDataModel
    var clientDetailObj: Client.Data.ClientData? = null

    fun setBinder(binder: ActivityClientsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbar.topBarClickListener = SlideMenuClickListener()
        binder.topbar.isSettingShow = true
        binder.topbar.isCenterTextShow = true
        clientDataModel = ClientDataModel()
        visitHistoryDataModel = VisitHistoryDataModel()
        init()
    }

    private fun init() {
//        initDrawer(mContext)
        clientsDataAdapter = ClientsDataAdapter(mContext)
        binder.rvAddaress.adapter = clientsDataAdapter
        historyDataAdapter = HistoryDataAdapter(mContext)
        binder.rvVisitHistory.adapter = historyDataAdapter
        clientsDataAdapter.setEventListener(object : ClientsDataAdapter.EventListener {
            override fun onItemClick(pos: Int, item: Client.Data.ClientData) {
                clientDetailObj = item
                binder.txtCompanyName.text = item.companyName
                binder.txtPersonName.text = item.firstName
                binder.txtArea.text = item.areaName
                binder.txtCity.text = item.city
                getClientVisitHistory(item.leadId)
//                getClient(item.leadId)
            }

        })

        historyDataAdapter.setEventListener(object : HistoryDataAdapter.EventListener {
            override fun onItemClick(pos: Int, item: VisitHistoryData.Data.VisitsHistory) {
                var intent = Intent(mContext, TaskDetailsDescriptionActivity::class.java)
                intent.putExtra("visitId", item.visitId)
                intent.putExtra("status", item.status)
                mContext.startActivity(intent)
            }
        })
        getClients()
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

    fun onBackPressed() {
        if(popUpwindow != null){
            popUp.closedPopup(popUpwindow!!)
        }
    }

    fun getClients() {
        showDialog("", mContext as Activity)
        clientDataModel.getClients(mContext)
            .observeForever { clientData ->
                dismissDialog()
                onCallResult(clientData)
            }
    }

    private fun onCallResult(clientData: Client?) = try {
        if (clientData != null) {
            when (clientData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    clientsDataAdapter.addAll(clientData.data?.clients!!)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast(clientData.data?.message)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(clientData.data?.message)
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getClientVisitHistory(client_id: Int?) {
        showDialog("", mContext as Activity)
        visitHistoryDataModel.client_id = client_id.toString()
        visitHistoryDataModel.getVisitHistory(mContext)
            .observeForever { visitHistoryData ->
                dismissDialog()
                onCallResult(visitHistoryData)
            }
    }

    private fun onCallResult(visitHistoryData: VisitHistoryData?) = try {
        if (visitHistoryData != null) {
            when (visitHistoryData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    historyDataAdapter.addAll(visitHistoryData.data?.visitsHistory!!)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast(visitHistoryData.message)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(visitHistoryData.message)
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getClient(id: Int) {
        showDialog("", mContext as Activity)
        clientDataModel.leadId = id.toString()
        clientDataModel.getClientDetails(mContext)
            .observeForever { clientDetails ->
                dismissDialog()
                onCallResult(clientDetails)
            }
    }

    private fun onCallResult(clientDetails: ClientDetails?) = try {
        if (clientDetails != null) {
            when (clientDetails.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    clientDetailObj = clientDetails.data?.clients?.get(0)
                    binder.txtCompanyName.text = clientDetailObj?.companyName
                    binder.txtPersonName.text = clientDetailObj?.firstName
                    binder.txtArea.text = clientDetailObj?.areaName
                    binder.txtCity.text = clientDetailObj?.city

                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast(clientDetails.data?.message)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(clientDetails.data?.message)
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
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

        fun onCreateNewTask(view: View) {
            try {
                if (clientDetailObj != null) {
                    var intent = Intent(mContext, CreateNewTaskActivity::class.java)
                    intent.putExtra("ClientDetail", clientDetailObj)
                    mContext.startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}