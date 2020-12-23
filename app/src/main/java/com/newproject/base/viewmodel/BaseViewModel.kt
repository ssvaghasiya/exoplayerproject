package com.newproject.base.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.newproject.R
import com.newproject.apputils.Constant
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.newproject.apputils.Debug
import com.newproject.apputils.MenuItem
import com.newproject.apputils.Utils
import com.newproject.base.utils.SideMenuAdapter
import com.newproject.databinding.CustomSideMenuBinding
import com.newproject.interfaces.CallbackListener
import com.newproject.ui.clients.view.ClientsActivity
import com.newproject.ui.home.datamodel.*
import com.newproject.ui.home.utils.PopUp
import com.newproject.ui.home.view.HomeActivity
import com.newproject.ui.login.datamodel.LogoutData
import com.newproject.ui.login.datamodel.LogoutDataModel
import com.newproject.ui.login.view.LoginActivity
import com.newproject.ui.mytask.view.MyTaskActivity
import com.newproject.ui.taskdetail.taskdetaildesc.view.TaskDetailsDescriptionActivity
import com.newproject.ui.taskdetail.taskdetailstep1.view.TaskDetailsStep1Activity
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


open class BaseViewModel(application: Application) : AppViewModel(application) {
    lateinit var result: Drawer
    private lateinit var activity: Activity
    private lateinit var logoutDataModel: LogoutDataModel
    lateinit var customSideMenuBinding: CustomSideMenuBinding
    lateinit var checkInDataModel: CheckInDataModel
    lateinit var checkOutDataModel: CheckOutDataModel
    lateinit var attendanceDataModel: AttendanceDataModel
    lateinit var breakStartDataModel: BreakStartDataModel
    lateinit var breakEndDataModel: BreakEndDataModel
    var now: Calendar? = null
    var bCal: Calendar? = null
    var popUpwindow: PopupWindow? = null
    lateinit var popUp: PopUp
    lateinit var employeeAttendance: AttendanceData.Data.EmployeeAttendance

    fun finishActivity(mContext: Context) {

//        if (mContext is HomeActivity) {
//
//        } else
        (mContext as Activity).finish()

    }


    fun initDrawer(mContext: Context) {
        initDrawer((mContext as Activity), mContext)
    }

    private lateinit var mAdapter: SideMenuAdapter
    fun initDrawer(activity: Activity, mContext: Context) {
        this.activity = activity
        logoutDataModel = LogoutDataModel()
        customSideMenuBinding = DataBindingUtil.inflate(
            activity.layoutInflater,
            R.layout.custom_side_menu,
            null,
            false
        )
        mAdapter = SideMenuAdapter(activity)


        val linearLayoutManager = LinearLayoutManager(activity)
        customSideMenuBinding.rvMenuList.layoutManager = linearLayoutManager
//        loadFromCache(customSideMenuBinding, mContext)
//        getUserProfile(mContext, customSideMenuBinding)

        customSideMenuBinding.navHeader.setOnClickListener {
            try {
//                val i = Intent(activity, ProfileActivity::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                activity.startActivity(i)
//                finishActivity()
                result.drawerLayout.closeDrawers()
            } catch (e: Exception) {
            }
        }

//        customSideMenuBinding.rvMenuList.setDivider(R.drawable.recycler_view_divider)
        mAdapter.setEventListener(object : SideMenuAdapter.EventListener {

            override fun onMenuItemClick(position: Int, view: View) {
//                mAdapter.changeSelectedItemUi(position)
                onMenuItemClicked(mAdapter.getItem(position).menuId, view)
            }
        })

        customSideMenuBinding.rvMenuList.adapter = mAdapter
        val data = ArrayList<MenuItem>()
        data.add(MenuItem("1", R.drawable.ic_home, getLabelText(R.string.home)))
        data.add(MenuItem("2", R.drawable.ic_icon_awesome_tasks, getLabelText(R.string.My_Tasks)))
        data.add(
            MenuItem(
                "3",
                R.drawable.ic_icon_ionic_md_notifications_gray,
                getLabelText(R.string.Notifications)
            )
        )
        data.add(MenuItem("4", R.drawable.ic_doctor, getLabelText(R.string.Clients)))
        data.add(MenuItem("5", R.drawable.ic_metro_user, getLabelText(R.string.My_Account)))
        data.add(MenuItem("6", R.drawable.ic_logout, getLabelText(R.string.log_out)))
//
//
        mAdapter.addAll(data)

        result = DrawerBuilder()
            .withActivity(activity)
            .withCloseOnClick(true)
            .withSelectedItemByPosition(-1)
            .withCustomView(customSideMenuBinding.root)
            .withDrawerWidthDp(260)
            .withDisplayBelowStatusBar(false)
            .withTranslucentStatusBar(false)
            .withOnDrawerListener(object : Drawer.OnDrawerListener {
                override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {

                }

                override fun onDrawerClosed(drawerView: View?) {

                }

                override fun onDrawerOpened(drawerView: View?) {
                    try {
//                        val userProfile = Utils.getPref(mContext, Constant.USER_PROFILE, "")
//                        val userProfileData = Gson().fromJson<UserProfileData>(
//                            JSONObject(userProfile!!).toString(),
//                            object : TypeToken<UserProfileData>() {}.type
//                        )
//                        customSideMenuBinding.txtFullName.text = userProfileData.result!!.fullname
//                        customSideMenuBinding.txtUserName.text = userProfileData.result!!.username
//                        Utils.loadImage(
//                            customSideMenuBinding.imgProfile,
//                            userProfileData.result!!.profilePicUrl!!,
//                            mContext,
//                            R.color.lgray
//                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            })
            .build()

        result.drawerLayout.setScrimColor(Color.TRANSPARENT)
        result.drawerLayout.fitsSystemWindows = false
    }


    private fun onMenuItemClicked(menuId: String, view: View) {
        when (menuId) {
            "1" -> {
                if (activity is HomeActivity) {
                    hideMenu(true)
                } else {
                    val intent = Intent(activity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    activity.startActivity(intent)
                    hideMenu(false)
                }
            }

            "2" -> {
                if (activity is MyTaskActivity) {
                    hideMenu(true)
                } else {
                    popUp.cancelTimer()
                    val intent = Intent(activity, MyTaskActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    activity.startActivity(intent)
                    hideMenu(false)
                }
            }
            "3" -> {
                if (activity is TaskDetailsStep1Activity) {
                    hideMenu(true)
                } else {
//                    val intent = Intent(activity, TaskDetailsDescriptionActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    activity.startActivity(intent)
//                    hideMenu(false)
                }
            }

            "4" -> {
                if (activity is ClientsActivity) {
                    hideMenu(true)
                } else {
                    popUp.cancelTimer()
                    val intent = Intent(activity, ClientsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    activity.startActivity(intent)
                    hideMenu(false)
                }
            }

            "5" -> {
                if (activity is TaskDetailsDescriptionActivity) {
                    hideMenu(true)
                } else {
//                    val intent = Intent(activity, TaskDetailsDescriptionActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    activity.startActivity(intent)
//                    hideMenu(false)
                }

            }
            "6" -> {
                getCategory(view.context)
            }
        }
    }

    fun getCategory(context: Context) {
        isInternetAvailable(context, object : CallbackListener {
            override fun onSuccess() {
                logoutDataModel.logOut(context).observeForever { categoryData ->
                    onCallResult(categoryData, context)
                }
            }

            override fun onCancel() {
            }

            override fun onRetry() {
                getCategory(context)
            }
        })
    }

    private fun onCallResult(logoutData: LogoutData, context: Context) = try {
        when (logoutData.statusCode) {
            Constant.RESPONSE_SUCCESS_CODE -> {
                showToast(logoutData.message)
                Utils.clearLoginCredentials(context)
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Constant.FINISH_ACTIVITY))
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            Constant.RESPONSE_FAILURE_CODE -> {
                showToast(logoutData.message)
            }
            else -> {
                showToast(logoutData.message)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    private fun hideMenu(b: Boolean) {
        try {
            result.closeDrawer()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onTopMenuClick() {
        toggleDrawer()
    }

    private fun toggleDrawer() {
        if (result.isDrawerOpen) {
            result.closeDrawer()
        } else {
            result.openDrawer()
        }
    }

    private fun setupFullHeight(
        v: View,
        dialogInterface: DialogInterface,
        linearLayout: LinearLayout,
        mContext: Context
    ) {

        val bottomSheetBehavior = BottomSheetBehavior.from((v.getParent()) as View)
        val layoutParams = linearLayout.layoutParams
        val windowHeight = getWindowHeight(mContext)
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        linearLayout.layoutParams = layoutParams
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            R.id.design_bottom_sheet
        )
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(mContext: Context): Int { // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (mContext as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    interface PermissionListener {
        fun onGranted()

        fun onDenied()
    }

//    fun checkPermission(activity: Activity, permissionsListener: PermissionListener) {
//        Dexter.withActivity(activity)
//            .withPermissions(
//                android.Manifest.permission.INTERNET,
//                android.Manifest.permission.READ_SMS
//            ).withListener(object : MultiplePermissionsListener {
//
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                    Debug.e("onPermissionsChecked", "" + report.areAllPermissionsGranted())
//                    Debug.e("onPermissionsChecked", "" + report.isAnyPermissionPermanentlyDenied)
//
//                    if (report.areAllPermissionsGranted()) {
//                        if (permissionsListener != null)
//                            permissionsListener.onGranted()
//                    } else {
//                        if (permissionsListener != null)
//                            permissionsListener.onDenied()
////                            showAlertDialog(permissionsListener)
//                    }
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: List<PermissionRequest>,
//                    token: PermissionToken
//                ) {
//                    Debug.e("onPermissionRationale", "" + permissions.size)
//                    token.continuePermissionRequest()
//                }
//
//
//            }).check()
//    }

    fun checkPermissionStorageAndCamera(
        activity: Activity,
        permissionsListener: PermissionListener
    ) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    Debug.e("onPermissionsChecked", "" + report.areAllPermissionsGranted())
                    Debug.e("onPermissionsChecked", "" + report.isAnyPermissionPermanentlyDenied)

                    if (report.areAllPermissionsGranted()) {
                        permissionsListener.onGranted()
                    } else {
                        permissionsListener.onDenied()
                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    Debug.e("onPermissionRationale", "" + permissions.size)
                    token.continuePermissionRequest()
                }


            }).check()
    }

    @SuppressLint("SimpleDateFormat")
    public fun parseDate(time: String?, output: String?): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = output
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

    interface CheckInBreakListener {
        fun onAttendance(attendanceData: AttendanceData?)
        fun onCheckedIn()
        fun onCheckedOut()
        fun onBreakStart()
        fun onBreakEnd()
    }

    fun getCheckIn(mContext: Context, checkInBreakListener: CheckInBreakListener) {
        checkInDataModel = CheckInDataModel()
        showDialog("", mContext as Activity)
        checkInDataModel.getCheckIn(mContext)
            .observeForever { checkInData ->
                dismissDialog()
                onCallResult(checkInData, mContext, checkInBreakListener)
            }
    }

    private fun onCallResult(
        checkInData: CheckInData?,
        mContext: Context,
        checkInBreakListener: CheckInBreakListener
    ) = try {
        if (checkInData != null) {
            when (checkInData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    showToast(checkInData.data?.message)
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail CheckIn")
                        getEmployeeAttendance(mContext, checkInBreakListener)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast("Fail CheckIn")
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
            }


        } else {
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getCheckout(mContext: Context, checkInBreakListener: CheckInBreakListener) {
        checkOutDataModel = CheckOutDataModel()
        showDialog("", mContext as Activity)
        checkOutDataModel.getCheckOut(mContext)
            .observeForever { checkOutData ->
                dismissDialog()
                onCallResult(checkOutData, mContext, checkInBreakListener)
            }
    }

    private fun onCallResult(
        checkOutData: CheckOutData?,
        mContext: Context,
        checkInBreakListener: CheckInBreakListener
    ) = try {
        if (checkOutData != null) {
            when (checkOutData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    showToast(checkOutData.data?.message)
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail Checkout")
                        getEmployeeAttendance(mContext, checkInBreakListener)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast("Fail Checkout")
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
            }


        } else {
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getEmployeeAttendance(mContext: Context, checkInBreakListener: CheckInBreakListener) {
        attendanceDataModel = AttendanceDataModel()
        popUp = PopUp()
        attendanceDataModel.getEmployeeAttendance(mContext)
            .observeForever { attendanceData ->
                onCallResult(attendanceData, mContext, checkInBreakListener)
            }
    }

    private fun onCallResult(
        attendanceData: AttendanceData?,
        mContext: Context,
        checkInBreakListener: CheckInBreakListener
    ) = try {

        if (checkInBreakListener != null) {
            checkInBreakListener.onAttendance(attendanceData)
        } else {

        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getBreakStart(mContext: Context, checkInBreakListener: CheckInBreakListener) {
        breakStartDataModel = BreakStartDataModel()
        breakStartDataModel.getBreakStart(mContext)
            .observeForever { breakStartData ->
                onCallResult(breakStartData, mContext, checkInBreakListener)
            }
    }

    private fun onCallResult(
        breakStartData: BreakStartData?,
        mContext: Context,
        checkInBreakListener: CheckInBreakListener
    ) = try {
        if (breakStartData != null) {
            when (breakStartData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    showToast(breakStartData.data?.message)
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail Break Start")
                        getEmployeeAttendance(mContext, checkInBreakListener)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    getEmployeeAttendance(mContext, checkInBreakListener)
                    showToast("Fail Break Start")
                }
            }
        } else {
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getBreakEnd(mContext: Context, checkInBreakListener: CheckInBreakListener) {
        breakEndDataModel = BreakEndDataModel()
        breakEndDataModel.getBreakEnd(mContext)
            .observeForever { breakEndData ->
                onCallResult(breakEndData, mContext, checkInBreakListener)
            }
    }

    private fun onCallResult(
        breakEndData: BreakEndData?,
        mContext: Context,
        checkInBreakListener: CheckInBreakListener
    ) = try {
        if (breakEndData != null) {
            when (breakEndData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    popUp.closedPopup(popUpwindow!!)
                    getEmployeeAttendance(mContext, checkInBreakListener)
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail Break End")
                        getEmployeeAttendance(mContext, checkInBreakListener)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    getEmployeeAttendance(mContext, checkInBreakListener)
                    showToast("Fail Break End")
                }
            }
        } else {
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
