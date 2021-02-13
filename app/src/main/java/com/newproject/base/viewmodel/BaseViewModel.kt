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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
import com.newproject.ui.home.view.HomeActivity
import com.newproject.ui.login.datamodel.LogoutData
import com.newproject.ui.login.datamodel.LogoutDataModel
import com.newproject.ui.login.view.LoginActivity
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.newproject.ui.MyApplication
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


open class BaseViewModel(application: Application) : AppViewModel(application) {
    lateinit var result: Drawer
    private lateinit var activity: Activity
    lateinit var customSideMenuBinding: CustomSideMenuBinding

    val db: FirebaseFirestore?
        get() {
            return (getApplication() as MyApplication).db
        }

    val auth: FirebaseAuth?
        get() {
            return (getApplication() as MyApplication).auth
        }

    val storageRef: StorageReference?
        get() {
            return (getApplication() as MyApplication).storageRef
        }

    val firebaseStorage: FirebaseStorage?
        get() {
            return (getApplication() as MyApplication).firebaseStorage
        }


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

            }
            "3" -> {

            }

            "4" -> {

            }

            "5" -> {

            }
            "6" -> {
            }
        }
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

}
