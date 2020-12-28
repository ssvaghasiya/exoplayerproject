package com.newproject.base.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mikepenz.materialdrawer.BuildConfig
import com.newproject.R
import com.newproject.apputils.*
import com.newproject.interfaces.CallbackListener


open class BaseActivity : AppCompatActivity() {

    internal var ad: AsyncProgressDialog? = null

    internal lateinit var commonReciever: MyServiceReciever

    val activity: BaseActivity
        get() = this

    private var checkActivity: AppCompatActivity? = null

    private var tvTitleText: TextView? = null

    internal lateinit var toast: Toast


    internal lateinit var baseCallback: BaseCallback

    @SuppressLint("ShowToast")
    public override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        toast = Toast.makeText(activity, "", Toast.LENGTH_LONG)

        val intentFilter = IntentFilter()
        intentFilter.addAction(Constant.FINISH_ACTIVITY)
        intentFilter.addAction(Constant.APP_GOES_BACKGROUND)
        commonReciever = MyServiceReciever()
        LocalBroadcastManager.getInstance(this).registerReceiver(
                commonReciever, intentFilter)

//        if (activity is CheckEligibilityActivity ||
//                activity is DashboardEvaluationActivity ||
//                activity is DashboardRequestLoanActivity ||
//                activity is DashboardNoLoansActivity ||
//                activity is LoanApprovedActivity ||
//                activity is RequestLoanActivity ||
//                activity is PersonalDetailActivity ||
//                activity is DepositDetailActivity ||
//                activity is CardDetailActivity) {
//
//            setStatusBarLight()
//        }

        setStatusBarLight()
    }

    private fun setStatusBarLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            window.statusBarColor = Color.WHITE
        }
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    fun setStatusBarGradient(activity: Activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
//            window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
//            window.setBackgroundDrawableResource(R.drawable.top_bar_background)
//
//        }
//    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LanguageHelper.setLocale(this)
    }

    override fun attachBaseContext(context: Context) {
        val lang = LanguageHelper.getLanguage(context).toString()
        Debug.e("--------", "lang MyApplication: $lang")
        super.attachBaseContext(LanguageHelper.setLocale(context))

    }


    internal inner class MyServiceReciever : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (intent.action!!.equals(
                                Constant.FINISH_ACTIVITY, ignoreCase = true)) {
                    finish()
                }

                if (intent.action!!.equals(Constant.APP_GOES_BACKGROUND, ignoreCase = true)) {
//                    if (activity is CheckEligibilityActivity) {
//                        finish()
//                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun showDialog(msg: String) {
        try {
            if (ad != null && ad!!.isShowing) {
                return
            }
            ad = AsyncProgressDialog.getInstant(activity)
            ad!!.show(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setMessage(msg: String) {
        try {
            ad!!.setMessage(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissDialog() {
        try {
            if (ad != null) {
                ad!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val handled = super.onKeyDown(keyCode, event)

        // Eat the long press event so the keyboard doesn't come up.
        return if (keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress) {
            true
        } else handled

    }

    fun showToast(text: String, duration: Int) {
        runOnUiThread {
            toast.setText(text)
            toast.duration = duration
            toast.show()
        }
    }


    override fun onDestroy() {
        try {
            LocalBroadcastManager.getInstance(applicationContext)
                    .unregisterReceiver(commonReciever)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }


    internal interface BaseCallback {
        fun onMasterDataLoad()
    }

//    fun checkPermissions(activity: AppCompatActivity) {
//        // Initialize a list of required permissions to request runtime
//        this.checkActivity = activity
////        val list = listOf<String>(
////                Manifest.permission.INTERNET,
////                Manifest.permission.CAMERA,
////                Manifest.permission.READ_EXTERNAL_STORAGE,
////                Manifest.permission.WRITE_EXTERNAL_STORAGE,
////                Manifest.permission.ACCESS_FINE_LOCATION,
////                Manifest.permission.ACCESS_COARSE_LOCATION,
////                Manifest.permission.READ_SMS
////        )
//        val list = listOf<String>(
//                Manifest.permission.INTERNET,
//                Manifest.permission.READ_SMS
//        )
//
//        // Initialize a new instance of ManagePermissions class
//        managePermissions = ManagePermissionsImp(this, list, PermissionsRequestCode)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            managePermissions.checkPermissions(object : ManagePermissionsImp.IPermission {
//                override fun onPermissionGranted() {
//                    if (activity is SplashActivity) {
//                        activity.startApplication(1000)
//                    }
//                }
//            })
//        } else {
//            if (activity is SplashActivity) {
//                activity.startApplication(1000)
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        when (requestCode) {
//            PermissionsRequestCode -> {
//                if (checkActivity is SplashActivity) {
//                    (checkActivity as SplashActivity).startApplication(1000)
//                }
//                return
//            }
//        }
//    }


    fun isInternetAvailable(context: Context, callbackListener: CallbackListener) {
        if (Utils.isInternetConnected(activity)) {
            callbackListener.onSuccess()
            return
        }
        AlertDialogHelper.showNoInternetDialog(context, callbackListener)
    }

    fun finishActivity() {
//        if (activity is MainActivity) {
//        } else {
//            activity.finish()
//        }
    }
    interface PermissionListener {
        fun onGranted()

        fun onDenied()
    }

    fun checkPermission(activity: Activity, permissionsListener: PermissionListener) {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.INTERNET
                ).withListener(object : MultiplePermissionsListener {

                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        Debug.e("onPermissionsChecked", "" + report.areAllPermissionsGranted())
                        Debug.e("onPermissionsChecked", "" + report.isAnyPermissionPermanentlyDenied)

                        if (report.areAllPermissionsGranted()) {
                            permissionsListener.onGranted()
                        } else {
                            permissionsListener.onDenied()
                            showPermissionAlert()
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

    var dialog: AlertDialog? = null
    fun showPermissionAlert() {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle(getString(R.string.need_permission_title))
        builder.setCancelable(false)
        builder.setMessage(getString(R.string.err_need_permission_msg))
        builder.setPositiveButton(R.string.btn_ok) { dialog, which ->
            startActivity(
                    Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    )
            )
            finish()
        }
        builder.setNeutralButton(R.string.btn_cancel) { dialog, which -> finish() }
        dialog = builder.create()
        dialog!!.show()

    }
}
