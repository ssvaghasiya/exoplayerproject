package com.newproject.base.viewmodel

import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.newproject.apputils.*
import com.newproject.interfaces.CallbackListener
import com.newproject.interfaces.DateEventListener
import java.util.*


open class AppViewModel : AndroidViewModel {


    internal var ad: AsyncProgressDialog? = null
    internal lateinit var toast: Toast

    constructor(application: Application) : super(application) {
        initToast()
    }

    fun initToast() {
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_LONG)

    }

    fun showDialog(msg: String, activity: Activity) {
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

    fun dismissDialog() {
        try {
            if (ad != null) {
                ad!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getContext(): Context {
        return (getApplication() as Application).applicationContext
    }

    fun showToast(text: String?) {

        toast.setText(text)
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }

    fun showToast(resId: Int) {
        showToast(getContext().getString(resId))
    }

    fun getLabelText(resId: Int): String {
        return getContext().getString(resId)
    }

    fun isInternetAvailable(context: Context, callbackListener: CallbackListener) {
        if (Utils.isInternetConnected(getContext())) {
            callbackListener.onSuccess()
            return
        }
        AlertDialogHelper.showNoInternetDialog(context, callbackListener)
    }

    fun showHttpError(context: Context) {
        AlertDialogHelper.showHttpExceptionDialog(context)
    }

    fun showDatePickerDialog(context: Context, eventListener: DateEventListener?, isTimePicker: Boolean) {
        val calendarNext = Calendar.getInstance()
//        calendarNext.time = Date(selectedTime)
        calendarNext.add(Calendar.DAY_OF_MONTH, 0);
        val mYear = calendarNext.get(Calendar.YEAR)
        val mMonth = calendarNext.get(Calendar.MONTH)
        val mDay = calendarNext.get(Calendar.DAY_OF_MONTH)

        val mDatePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            var selectedmonth = selectedmonth
            selectedmonth += 1
            if (isTimePicker) {
            } else {
                val date = Utils.parseTime(selectedday.toString() + "/" + selectedmonth + "/" + selectedyear, "dd/MM/yyyy")
                eventListener?.onDateSelected(date)
            }
        }, mYear, mMonth, mDay)
        mDatePicker.datePicker.maxDate = Calendar.getInstance().timeInMillis
        mDatePicker.show()
    }

    fun showDatePickerDialog(context: Context, selectedTime: Long, eventListener: DateEventListener?, isTimePicker: Boolean) {
        val calendarNext = Calendar.getInstance()
        calendarNext.time = Date(selectedTime)
        calendarNext.add(Calendar.DAY_OF_MONTH, 0);
        val mYear = calendarNext.get(Calendar.YEAR)
        val mMonth = calendarNext.get(Calendar.MONTH)
        val mDay = calendarNext.get(Calendar.DAY_OF_MONTH)

        val mDatePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            var selectedmonth = selectedmonth
            selectedmonth += 1
            if (isTimePicker) {
            } else {
                val date = Utils.parseTime(selectedday.toString() + "/" + selectedmonth + "/" + selectedyear, "dd/MM/yyyy")
                eventListener?.onDateSelected(date)
            }
        }, mYear, mMonth, mDay)
        mDatePicker.datePicker.maxDate = Calendar.getInstance().timeInMillis
        mDatePicker.show()
    }

    fun showCardExpiryDatePickerDialog(context: Context, selectedTime: Long, eventListener: DateEventListener?, isTimePicker: Boolean) {
        val calendarNext = Calendar.getInstance()
        calendarNext.time = Date(selectedTime)
        calendarNext.add(Calendar.DAY_OF_MONTH, 0);
        val mYear = calendarNext.get(Calendar.YEAR)
        val mMonth = calendarNext.get(Calendar.MONTH)
        val mDay = calendarNext.get(Calendar.DAY_OF_MONTH)


        val mDatePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            var selectedmonth = selectedmonth
            selectedmonth += 1
            if (isTimePicker) {
            } else {
                val date = Utils.parseTime(selectedday.toString() + "/" + selectedmonth + "/" + selectedyear, "dd/MM/yyyy")
                eventListener?.onDateSelected(date)
            }
        }, mYear, mMonth, mDay)
        mDatePicker.datePicker.minDate = Calendar.getInstance().timeInMillis
        mDatePicker.show()
    }

    fun showTimePickerDialog(context: Context, date: Date, eventListener: DateEventListener?) {
        val c = Calendar.getInstance()
        c.time = date
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            Debug.e("TAG", "onTimeSet() called with: view = [$view], hourOfDay = [$hourOfDay], minute = [$minute]")

            //Date date = new Date(selectedyear, selectedmonth, selectedday, hourOfDay, minute, 0);
            val date = Utils.parseTime(c.get(Calendar.DAY_OF_MONTH).toString() + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + " " + hourOfDay + ":" + minute + ":00", "dd/MM/yyyy HH:mm:ss")
            eventListener?.onDateSelected(date)
        }, hour, minute, false)
        timePicker.show()
    }

    fun logout() {
        Utils.delLatLong(getContext())
        Utils.clearLoginCredentials(getContext())
        Utils.deleteUserAuthToken(getContext())
        LocalBroadcastManager.getInstance(getContext())
                .sendBroadcast(Intent(Constant.FINISH_ACTIVITY))

    }

    fun getLatitude(): String? {
        return Utils.getLatitude(getContext()).toString()
    }

    fun getLongitude(): String? {
        return Utils.getLongitude(getContext()).toString()
    }

//    fun isLocationPermissionAllowed(context: Context): Boolean {
//        val list = listOf<String>(
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//        val managePermissions = ManagePermissionsImp((context as MainActivity), list, 0)
//        if (managePermissions.isPermissionsGranted() == 0) {
//            return true
//        }
//        return false
//    }

    fun hideKeyboard(view: View) {
        Utils.hideKeyBoard(getContext(), view)
    }
}
