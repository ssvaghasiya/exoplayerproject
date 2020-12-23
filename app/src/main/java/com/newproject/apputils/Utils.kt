@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.newproject.apputils

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Typeface
import android.location.LocationManager
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.newproject.BuildConfig
import com.newproject.exceptions.networks.NoInternetException
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

object Utils {

    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"

    val isSDcardMounted: Boolean
        get() {

            val state = Environment.getExternalStorageState()
            return state == Environment.MEDIA_MOUNTED && state != Environment.MEDIA_MOUNTED_READ_ONLY

        }

    fun getHashKey() {
        val sha1 = "0f:04:66:e6:ee:a8:27:cb:3a:c9:42:6f:03:1c:14:e0:fd:fe:ec:45"
        val arr = sha1.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val byteArr = ByteArray(arr.size)

        for (i in arr.indices) {
            byteArr[i] = Integer.decode("0x" + arr[i]).toByte()
        }

        Debug.e("KeyHash 2 : ", Base64.encodeToString(byteArr, Base64.NO_WRAP))
    }

    fun isEmptyString(str: String?): Boolean {
        return str != null && str != ""
    }


    /**
     * Create a File for saving an image or video
     *
     * @return Returns file reference
     */
    // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.
    // This location works best if you want the created images to be
    // shared
    // between applications and persist after your app has been
    // uninstalled.
    // Create the storage directory if it does not exist
    // Create a media file name
    val outputMediaFile: File?
        get() {
            try {
                val mediaStorageDir: File
                if (isSDcardMounted) {
                    mediaStorageDir = File(Constant.FOLDER_RIDEINN_PATH)
                } else {
                    mediaStorageDir = File(
                        Environment.getDataDirectory(),
                        Constant.FOLDER_NAME
                    )
                }
                if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                    return null
                }

                val mediaFile = File(
                    mediaStorageDir.path
                            + File.separator + Date().time + ".jpg"
                )
                mediaFile.createNewFile()

                return mediaFile
            } catch (e: Exception) {
                return null
            }

        }

    fun getOutputMedia(c: Context): File? {
//        val outputMediaFile: File?
        try {
            val mediaStorageDir: File = c.filesDir
//            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
//                return null
//            }

            val mediaFile = File(
                mediaStorageDir.path
                        + File.separator + Date().time + ".jpg"
            )
            mediaFile.createNewFile()

            return mediaFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun setPref(c: Context, pref: String, value: String) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putString(pref, value)
        e.apply()

    }


    fun getPref(c: Context, pref: String, value: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(
            pref,
            value
        )
    }

    fun setPref(c: Context, pref: String, value: Float) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putFloat(pref, value)
        e.apply()

    }

    fun getPref(c: Context, pref: String, value: Float): Float? {
        return PreferenceManager.getDefaultSharedPreferences(c).getFloat(
            pref,
            value
        )
    }

    fun setPref(c: Context, pref: String, value: Boolean) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putBoolean(pref, value)
        e.apply()

    }

    fun getPref(c: Context, pref: String, value: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
            pref, value
        )
    }

    fun delPref(c: Context, pref: String) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.remove(pref)
        e.commit()
    }

    fun setPref(c: Context, pref: String, value: Int) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putInt(pref, value)
        e.commit()

    }

    fun getPref(c: Context, pref: String, value: Int): Int {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(
            pref,
            value
        )
    }

    fun setPref(c: Context, pref: String, value: Long) {
        val e = PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putLong(pref, value)
        e.commit()
    }

    fun getPref(c: Context, pref: String, value: Long): Long {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(
            pref,
            value
        )
    }

    fun setPref(c: Context, file: String, pref: String, value: String) {
        val settings = c.getSharedPreferences(
            file,
            Context.MODE_PRIVATE
        )
        val e = settings.edit()
        e.putString(pref, value)
        e.commit()
    }

    fun getPref(c: Context, file: String, pref: String, value: String): String? {
        return c.getSharedPreferences(file, Context.MODE_PRIVATE).getString(
            pref, value
        )
    }

//    fun getUserName(c: Context): String? {
//        val login = Utils.getPref(c, Constant.LOGIN_INFO, "")
//        val loginData = Gson().fromJson<LoginData>(
//                JSONObject(login!!).toString(), object : TypeToken<LoginData>() {}.type
//        )
//        return loginData.data!!.userName
//    }
//    fun getName(c: Context): String? {
//        val login = Utils.getPref(c, Constant.LOGIN_INFO, "")
//        val loginData = Gson().fromJson<LoginData>(
//                JSONObject(login!!).toString(), object : TypeToken<LoginData>() {}.type
//        )
//        return loginData.data!!.name
//    }
//    fun getEmail(c: Context): String? {
//        val login = Utils.getPref(c, Constant.LOGIN_INFO, "")
//        val loginData = Gson().fromJson<LoginData>(
//                JSONObject(login!!).toString(), object : TypeToken<LoginData>() {}.type
//        )
//        return loginData.data!!.email
//    }
//
//    fun getCoverImage(c: Context): String? {
//        val login = Utils.getPref(c, Constant.LOGIN_INFO, "")
//        val loginData = Gson().fromJson<LoginData>(
//                JSONObject(login!!).toString(), object : TypeToken<LoginData>() {}.type
//        )
//        return loginData.data!!.coverImage
//    }
//
//    fun getAvatar(c: Context): String? {
//        val login = Utils.getPref(c, Constant.LOGIN_INFO, "")
//        val loginData = Gson().fromJson<LoginData>(
//                JSONObject(login!!).toString(), object : TypeToken<LoginData>() {}.type
//        )
//        return loginData.data!!.avatar
//    }

    fun validateEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches()
        }
    }

    fun validate(target: String, pattern: String): Boolean {
        if (TextUtils.isEmpty(target)) {
            return false
        } else {
            val r = Pattern.compile(pattern)
            return r.matcher(target)
                .matches()
        }
    }

    fun isAlphaNumeric(target: String): Boolean {
        if (TextUtils.isEmpty(target)) {
            return false
        } else {
            val r = Pattern.compile("^[a-zA-Z0-9]*$")
            return r.matcher(target)
                .matches()
        }
    }

    fun isNumeric(target: String): Boolean {
        if (TextUtils.isEmpty(target)) {
            return false
        } else {
            val r = Pattern.compile("^[0-9]*$")
            return r.matcher(target)
                .matches()
        }
    }

    fun getDeviceWidth(context: Context): Int {
        try {
            val metrics = context.resources.displayMetrics
            return metrics.widthPixels
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return 480
    }


    fun getDeviceHeight(context: Context): Int {
        try {
            val metrics = context.resources.displayMetrics
            return metrics.heightPixels
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return 800
    }

    @Throws(NoInternetException::class)
    fun isInternetConnected(mContext: Context?): Boolean {
        var outcome = false
        try {
            if (mContext != null) {
                val cm = mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = cm.activeNetworkInfo
                if (networkInfo!!.isConnected) {
                    outcome = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return outcome
    }

    fun getDeviceId(c: Context): String? {
        val deviceId = Constant.NO_DEVICE_ID
        var aid: String?
        try {
            aid = ""
            aid = Settings.Secure.getString(
                c.contentResolver,
                "android_id"
            )

            if (aid == null) {
                aid = deviceId
            } else if (aid.isEmpty()) {
                aid = deviceId
            }

        } catch (e: Exception) {
            sendExceptionReport(e)
            aid = deviceId
        }

        return aid

    }

    fun random(min: Float, max: Float): Float {
        return (min + Math.random() * (max - min + 1)).toFloat()
    }

    fun random(min: Int, max: Int): Int {
        return Math.round((min + Math.random() * (max - min + 1)).toFloat())
    }

    fun hasFlashFeature(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_FLASH
        )
    }

//    fun hasCameraFeature(context: Context): Boolean {
//        return context.packageManager.hasSystemFeature(
//                PackageManager.FEATURE_CAMERA)
//    }

    fun hideKeyBoard(c: Context, v: View) {
        val imm = c
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun getBoldC(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "SF-Compact-Display-Bold.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    fun getBold(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-Bold.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getRegular(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-Regular.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    fun getExtraBold(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-ExtraBold.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getSemiBold(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-SemiBold.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getLight(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-Light.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    fun getBoldExtra(c: Context): Typeface? {
        try {
            return Typeface.createFromAsset(
                c.assets,
                "Overpass-ExtraBold.ttf"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun formatNo(str: String, pattern: String = "#.##", force2Decimal: Boolean = false): String? {
        val number = removeComma(nullSafe(str))
        return if (!TextUtils.isEmpty(number)) {
            formatToComma(number!!, pattern, force2Decimal)
        } else number

    }

    fun formatNoDollar(str: String, pattern: String = "#.##"): String? {
        val number = removeComma(nullSafe(str))
        if (!TextUtils.isEmpty(number)) {

            var finalStr = formatToComma(number!!, pattern)

            if (!finalStr!!.startsWith("$"))
                finalStr = "$$finalStr"
            return finalStr
        }

        return number

    }

    private fun formatToComma(
        str: String,
        pattern: String,
        force2Decimal: Boolean = false
    ): String? {
//        var number = removeComma(nullSafe(str))
        var number: String = str
        var result: String = ""
        if (!TextUtils.isEmpty(number)) {

            if (number!!.contains(".")) {
                number = truncateUptoTwoDecimal(number, force2Decimal)
                if (!force2Decimal) {
                    val decimalFormat = DecimalFormat(pattern)
                    number = decimalFormat.format(BigDecimal(number))
                }
            }

            result = NumberFormat.getNumberInstance(Locale.US).format(number.toDouble())
            if (force2Decimal) {
                val decimalIndex = result.indexOf(".")
                if (decimalIndex != -1) {
                    val decimalString = result.substring(decimalIndex + 1)
                    if (decimalString.length == 1) {
                        result += "0"
                    }
                }
            }
            return result
        }

        return number
    }

    private fun truncateUptoTwoDecimal(
        doubleValue: String,
        force2Decimal: Boolean = false
    ): String {
        if (doubleValue != null) {
            var result = doubleValue
            val decimalIndex = result.indexOf(".")
            if (decimalIndex != -1) {
                val decimalString = result.substring(decimalIndex + 1)
                if (decimalString.length > 2) {
                    result = doubleValue.substring(0, decimalIndex + 3)
                } else if (decimalString.length == 1) {
                    if (force2Decimal) {
                        result = String.format(
                            Locale.ENGLISH, "%.2f",
                            doubleValue.toDouble()
                        );
                    }
                }
            }
            return result
        }

        return doubleValue
    }

    fun removeComma(str: String): String? {
        var mValue = str
        try {
            if (!TextUtils.isEmpty(mValue)) {
                mValue = mValue.replace(",".toRegex(), "")
                try {
                    val format = NumberFormat.getCurrencyInstance()
                    val number = format.parse(mValue)
                    return number.toString()
                } catch (e: ParseException) {
//                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
//            e.printStackTrace()
        }

        Debug.e("removeComma", "" + mValue)
        return str

    }


    fun sendExceptionReport(e: Exception) {
        e.printStackTrace()

        try {
            // Writer result = new StringWriter();
            // PrintWriter printWriter = new PrintWriter(result);
            // e.printStackTrace(printWriter);
            // String stacktrace = result.toString();
            // new CustomExceptionHandler(c, URLs.URL_STACKTRACE)
            // .sendToServer(stacktrace);
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }


    fun getAndroidId(c: Context): String {
        var aid: String?
        try {
            aid = Settings.Secure.getString(
                c.contentResolver,
                "android_id"
            )

            if (aid == null) {
                aid = Constant.NO_DEVICE_ID
            } else if (aid.length <= 0) {
                aid = Constant.NO_DEVICE_ID
            }
        } catch (e: Exception) {
            e.printStackTrace()
            aid = Constant.NO_DEVICE_ID
        }

        Debug.e("", "aid : " + aid!!)

        return aid

    }

    fun getAppVersionCode(c: Context): Int {
        try {
            return c.packageManager.getPackageInfo(c.packageName, 0).versionCode
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return 0

    }

    fun getPhoneModel(): String {
        try {
            return Build.MODEL
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return ""
    }

    fun getPhoneBrand(): String {

        try {
            return Build.BRAND
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return ""
    }

    fun getOsVersion(): String {

        try {
            return Build.VERSION.RELEASE
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return ""
    }

    fun getOsAPILevel(): Int {

        try {
            return Build.VERSION.SDK_INT
        } catch (e: Exception) {
            sendExceptionReport(e)
        }

        return 0
    }

    fun parseCalendarFormat(c: Calendar, pattern: String): String {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        return sdf.format(c.time)
    }

    fun parseTime(time: Long, pattern: String): String {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        return sdf.format(Date(time))
    }

    fun parseTime(time: String, pattern: String): Date {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        try {
            return sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    fun parseTime(
        time: String, fromPattern: String,
        toPattern: String
    ): String {
        var sdf = SimpleDateFormat(
            fromPattern,
            Locale.getDefault()
        )
        try {
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(toPattern, Locale.getDefault())
            return sdf.format(d)
        } catch (e: Exception) {
            Log.i("parseTime", "" + e.message)
        }

        return ""
    }

    fun formatAmount(amount: Double): String {
        return String.format(Locale.US, "%.2f", amount)
    }


    fun parseTime(time: String): String {
        return parseTime(time, "yyyy-MM-dd'T'HH:mm:ss", "MMM dd")
    }

    fun getTransactionDate(time: String): String {
        return parseTime(time, "yyyy-MM-dd'T'HH:mm:ss.SSS", "MM/dd/yyyy hh:mm")
    }

    fun getTransactionStatus(status: String): String {
        return status
    }

    fun getStartDate(time: String): String {
        return " " + parseTime(time, "yyyy-MM-dd'T'HH:mm:ss", "MMM dd") + " - "
    }

    fun getEndDate(time: String): String {
        return " " + parseTime(time, "yyyy-MM-dd'T'HH:mm:ss", "MMM dd")
    }

    fun getShareDateTime(time: String): String {
        return parseTime(time, "yyyy-MM-dd'T'HH:mm:ss", "dd MMMM yyyy hh:mm a")
    }

    fun parseTimeUTCtoDefault(time: String, pattern: String): Date {
        var sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = Calendar.getInstance().timeZone
            return sdf.parse(sdf.format(d))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Date()
    }

    fun parseTimeUTCtoDefault(time: Long): Date {

        try {
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            cal.timeInMillis = time
            val d = cal.time
            val sdf = SimpleDateFormat()
            sdf.timeZone = Calendar.getInstance().timeZone
            return sdf.parse(sdf.format(d))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Date()
    }

    fun parseTimeUTCtoDefault(
        time: String, fromPattern: String,
        toPattern: String
    ): String {

        var sdf = SimpleDateFormat(
            fromPattern,
            Locale.getDefault()
        )
        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val d = sdf.parse(time)
            sdf = SimpleDateFormat(toPattern, Locale.getDefault())
            sdf.timeZone = Calendar.getInstance().timeZone
            return sdf.format(d)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getUTCTime(): Date {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return calendar.time
    }

    fun getUTCCalendar(): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        return calendar
    }

    fun daysBetween(startDate: Date, endDate: Date): Long {
        val sDate = getDatePart(startDate)
        val eDate = getDatePart(endDate)

        var daysBetween: Long = 0
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1)
            daysBetween++
        }
        return daysBetween
    }

    fun getDatePart(date: Date): Calendar {
        val cal = Calendar.getInstance()       // get calendar instance
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)            // set hour to midnight
        cal.set(Calendar.MINUTE, 0)                 // set minute in hour
        cal.set(Calendar.SECOND, 0)                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0)            // set millisecond in second
        return cal
    }

    fun nullSafe(content: String?): String {
        if (content == null) {
            return ""
        }

        return if (content.equals("null", ignoreCase = true)) {
            ""
        } else content

    }

    fun nullSafe(content: String, defaultStr: String): String {
        if (TextUtils.isEmpty(content)) {
            return defaultStr
        }

        return if (content.equals("null", ignoreCase = true)) {
            defaultStr
        } else content

    }

    fun nullSafeDash(content: String): String {
        if (TextUtils.isEmpty(content)) {
            return "-"
        }

        return if (content.equals("null", ignoreCase = true)) {
            "-"
        } else content

    }

    fun nullSafe(content: Int, defaultStr: String): String {
        return if (content == 0) {
            defaultStr
        } else "" + content

    }


    fun isGPSProviderEnabled(context: Context): Boolean {
        val locationManager = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun isNetworkProviderEnabled(context: Context): Boolean {
        val locationManager = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun isLocationProviderEnabled(context: Context): Boolean {
        return isGPSProviderEnabled(context) || isNetworkProviderEnabled(context)
    }

    fun isLocationProviderRequired(context: Context): Boolean {
        val lang = getPref(context, Constant.USER_LONGITUDE, "")
        val lat = getPref(context, Constant.USER_LATITUDE, "")

        return !(lat!!.isNotEmpty() && lang!!.length > 0)

    }


    fun isFirsTime(c: Context): Boolean {
        return getPref(c, Constant.IS_FIRST_TIME, true)
    }

    fun getUid(c: Context): String? {
        return getPref(c, Constant.UID, "")
    }

    fun clearCart(c: Context) {
        delPref(c, Constant.CART_PRODUCT)
    }

    fun clearLoginCredentials(c: Context) {
        delPref(c, RequestParamsUtils.USER_ID)
        delPref(c, RequestParamsUtils.SESSION_ID)
        delPref(c, RequestParamsUtils.TOKEN)
        delPref(c, RequestParamsUtils.AUTHENTICATIONTOKEN)

        delPref(c, Constant.LOGIN_INFO)


        delPref(c, Constant.CITY_AREA)
        delPref(c, Constant.CATEGORY)
        delPref(c, Constant.ALL_CATEGORY)
        delPref(c, Constant.ALL_BUSINESS)
        delPref(c, Constant.EXPLORE)
        delPref(c, Constant.USER_FAVOURITES)
        delPref(c, Constant.USER_FOLLOWINGS)
        delPref(c, Constant.USER_FOLLOWERS)
        delPref(c, Constant.NOTIFICATION)
        delPref(c, Constant.OLD_ORDER)
        delPref(c, Constant.NEW_ORDER)
        delPref(c, Constant.SHIPPING_ADDRESS)
        delPref(c, Constant.CART_PRODUCT)
        delPref(c, Constant.ORDERS_DETAILS_DATA)
        delPref(c, Constant.SEARCH_FRIEND_DATA)
        delPref(c, Constant.HOME_FOLLOWING_DATA)

        delPref(c, Constant.DASHBOARD_INFO)
        delPref(c, Constant.BUSINESS_NEW_ORDER_DATA)
        delPref(c, Constant.BUSINESS_PAST_ORDER_DATA)
        delPref(c, Constant.BUSINESS_ORDER_DETAILS_DATA)
        delPref(c, Constant.PRODUCT_LINE_DATA)

//        val nMgr = c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        nMgr.cancelAll()
    }


    fun asList(str: String): ArrayList<String?> {

        return ArrayList<String?>(
            Arrays.asList<String>(
                *str
                    .split("\\s*,\\s*".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            )
        )
    }

    fun implode(data: ArrayList<String>): String {
        try {
            var devices = ""
            for (iterable_element in data) {
                devices = "$devices,$iterable_element"
            }

            if (devices.length > 0 && devices.startsWith(",")) {
                devices = devices.substring(1)
            }

            return devices
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun scanMediaForFile(context: Context, filePath: String) {
        resetExternalStorageMedia(context, filePath)
        notifyMediaScannerService(context, filePath)
    }

    fun resetExternalStorageMedia(
        context: Context,
        filePath: String
    ): Boolean {
        try {
            if (Environment.isExternalStorageEmulated())
                return false
            val uri = Uri.parse("file://" + File(filePath))
            val intent = Intent(Intent.ACTION_MEDIA_MOUNTED, uri)

            context.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    fun notifyMediaScannerService(
        context: Context,
        filePath: String
    ) {

        MediaScannerConnection.scanFile(context, arrayOf(filePath), null) { path, uri ->
            Debug.i("ExternalStorage", "Scanned $path:")
            Debug.i("ExternalStorage", "-> uri=$uri")
        }
    }

    fun cancellAllNotication(context: Context) {

        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    fun toInitCap(param: String?): String? {
        try {
            if (param != null && param.length > 0) {
                val charArray = param.toCharArray() // convert into char
                // array
                charArray[0] = Character.toUpperCase(charArray[0]) // set
                // capital
                // letter to
                // first
                // position
                return String(charArray) // return desired output
            } else {
                return ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return param
    }

    fun toGetInitChar(param: String?): String? {
        try {
            if (param != null && param.length > 0) {
                val charArray = param[0] // convert into char
                // array
                //                charArray[0] = Character.toUpperCase(charArray[0]); // set
                // capital
                // letter to
                // first
                // position
                val valstr = charArray.toString()
                return valstr // return desired output
            } else {
                return ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return param
    }

    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)

        Debug.e("LOOK", imageEncoded)
        return imageEncoded
    }

    fun decodeBase64(input: String): Bitmap {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    fun getExtenstion(urlPath: String): String {
        return if (urlPath.contains(".")) {
            urlPath.substring(urlPath.lastIndexOf(".") + 1)
        } else ""

    }

    fun getFileName(urlPath: String): String {
        return if (urlPath.contains(".")) {
            urlPath.substring(urlPath.lastIndexOf("/") + 1)
        } else ""

    }

    fun dpToPx(context: Context, value: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            r.displayMetrics
        )
    }

    fun spToPx(context: Context, value: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            value.toFloat(),
            r.displayMetrics
        )
    }


    fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        Debug.e("type", "" + type!!)
        return type
    }

    fun isJPEGorPNG(url: String): Boolean {
        try {
            val type = getMimeType(url)
            val ext = type!!.substring(type.lastIndexOf("/") + 1)
            if (ext.equals("jpeg", ignoreCase = true) || ext.equals(
                    "jpg",
                    ignoreCase = true
                ) || ext.equals("png", ignoreCase = true)
            ) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return true
        }

        return false
    }

    fun getFileSize(url: String): Double {
        val file = File(url)

        // Get length of file in bytes
        val fileSizeInBytes = file.length().toDouble()
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        val fileSizeInKB = fileSizeInBytes / 1024
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        val fileSizeInMB = fileSizeInKB / 1024

        Debug.e("fileSizeInMB", "" + fileSizeInMB)
        return fileSizeInMB
    }

    fun getAsteriskName(str: String): String {
        var str = str
        val n = 4
        str = nullSafe(str)
        val fStr = StringBuilder()
        if (!TextUtils.isEmpty(str)) {
            if (str.length > n) {
                fStr.append(str.substring(0, n))
                for (i in 0 until str.length - n) {
                    fStr.append("*")
                }

                return fStr.toString()
            } else {
                fStr.append(str.substring(0, str.length - 1))
            }
        }
        return str
    }

    fun getFCMToken(c: Context): String? {
        return getPref(c, Constant.FCM_TOKEN, "")
    }

    fun getUserToken(c: Context): String? {
        return getPref(c, RequestParamsUtils.TOKEN, "")
    }

//    fun getLoginData(c: Context): LoginData? {
//        val str = getPref(c, Constant.LOGIN_INFO, "")
//        if (str.isNullOrEmpty().not()) {
//            return Gson().fromJson<LoginData>(JSONObject(str).toString(), object : TypeToken<LoginData>() {}.type)
//        }
//
//        return null
//    }

    fun isUserLoggedIn(c: Context): Boolean? {
        val str = getPref(c, Constant.LOGIN_INFO, "")
        if (str.isNullOrEmpty().not()) {
            return true
        }

        return false
    }

    fun getUserAuthToken(c: Context): String? {
        return getPref(c, RequestParamsUtils.AUTHENTICATIONTOKEN, "")
    }

    fun deleteUserAuthToken(c: Context) {
        delPref(c, RequestParamsUtils.AUTHENTICATIONTOKEN)
    }

    fun setLatLong(context: Context, long: Float, lat: Float) {
        setPref(context, Constant.USER_LONGITUDE, long)
        setPref(context, Constant.USER_LATITUDE, lat)
    }

    fun getLatitude(context: Context) = getPref(context, Constant.USER_LATITUDE, 0.0f)

    fun getLongitude(context: Context) = getPref(context, Constant.USER_LONGITUDE, 0.0f)

    fun delLatLong(context: Context) {
        delPref(context, Constant.USER_LONGITUDE)
        delPref(context, Constant.USER_LATITUDE)
    }

    private var toast: Toast? = null
    fun showToast(context: Context, msg: String) {
        if (toast == null)
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
//        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
        toast!!.setText(msg)
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.show()
    }

    fun getCurrency(): String {
        return "$"
    }

    fun getAmount(amount: Int): String {
        return getCurrency() + " " + formatToFloat(amount.toDouble())
    }

    fun formatToFloat(flt: Double?): String {
        val decimalFormat = DecimalFormat("0.00")
        decimalFormat.isGroupingUsed = true
        decimalFormat.groupingSize = 3
        return decimalFormat.format(flt)
    }

    fun shareOn(shareText: String, shareMode: String, context: Context) {
        if (shareMode == Constant.SHARE_FACEBOOK) {
//            Utils.copyText(getActivity(),shareText,"Description copied! You can paste here")
            shareOnFb(shareText, context);
        } else if (shareMode == Constant.SHARE_TWITTER) {
            shareOnTwitter(shareText, context);
        } else if (shareMode == Constant.SHARE_INSTAGRAM) {
            shareOnInstagram(shareText, context);
        } else if (shareMode == Constant.SHARE_OTHER) {
            shareImageOther(shareText, context);
        }
    }

    private fun shareImageOther(shareText: String, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");
            context.startActivity(Intent.createChooser(intent, "Share"));
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun shareOnInstagram(shareText: String, context: Context) {
        try {
            val appPackageName = "com.instagram.android"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            if (isPackageInstalled("" + appPackageName, context)) {
                intent.setPackage("" + appPackageName)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Please Install Instagram Application", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareOnFb(shareText: String, context: Context) {
        try {
            val appPackageName = "com.facebook.katana"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            if (isPackageInstalled("" + appPackageName, context)) {
                intent.setPackage("" + appPackageName)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Please Install Facebook Application", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareOnTwitter(shareText: String, context: Context) {
        try {
            val appPackageName = "com.twitter.android"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            if (isPackageInstalled("" + appPackageName, context)) {
                intent.setPackage("" + appPackageName)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Please Install Twitter Application", Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isPackageInstalled(packagename: String, context: Context): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }

//    fun showSpinner(mContext: Context?,
//                    title: String, tv: TextView,
//                    data: ArrayList<Spinner>, isFilterable: Boolean, callback: SpinnerCallback?
//    ) {
//        val a = Dialog(mContext!!)
//        val w = a.window
//        a.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        a.setContentView(R.layout.spinner)
//        w!!.setBackgroundDrawableResource(android.R.color.transparent)
//
//        val lblselect = w.findViewById<View>(R.id.dialogtitle) as TextView
//        lblselect.text = title.replace("*", "").trim { it <= ' ' }
//
//        //        TextView dialogClear = (TextView) w.findViewById(R.id.dialogClear);
//        //        dialogClear.setVisibility(View.VISIBLE);
//        //        dialogClear.setOnClickListener(new View.OnClickListener() {
//        //            @Override
//        //            public void onClick(View view) {
//        //                tv.setText("");
//        //                tv.setTag(null);
//        //
//        //                a.dismiss();
//        //            }
//        //        });
//
//        val editSearch = w.findViewById<View>(R.id.editSearch) as EditText
//        if (isFilterable) {
//            editSearch.visibility = View.VISIBLE
//        } else {
//            editSearch.visibility = View.GONE
//        }
//
//        val adapter = SpinnerAdapter(mContext!!)
//        adapter.setFilterable(isFilterable)
//        val lv = w.findViewById<View>(R.id.lvSpinner) as ListView
//        lv.adapter = adapter
//        adapter.addAll(data)
//
//        lv.onItemClickListener = AdapterView.OnItemClickListener { adapterview, view, position, l ->
//            val selList = ArrayList<Spinner>()
//            selList.add(adapter.getItem(position))
//
//            tv.text = adapter.getItem(position).title
//            tv.tag = adapter.getItem(position).ID
//
//            callback?.onDone(selList)
//
//            a.dismiss()
//        }
//
//        editSearch.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//                if (editable.toString().trim { it <= ' ' }.length >= 1) {
//                    adapter.filter!!.filter(editable.toString().trim { it <= ' ' })
//                } else {
//                    adapter.filter!!.filter("")
//                }
//
//            }
//        })
//
//        a.show()
//    }


    fun loadImage(img: ImageView, url: String, context: Context, resId: Int) {
        run {
            val options: RequestOptions = RequestOptions()
                .placeholder(resId)
                .error(resId);
            Glide.with(context).load(url).apply(options).into(img)
        }
    }
//    fun loadImage(img: ImageView, url: String, context: Context) {
//        run {
//            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(img)
//        }
//    }

    fun isEventFree(rate: Int?): Boolean {
        if (rate != null) {
            return rate <= 0
        }
        return false
    }

    fun getEventAmount(rate: Int?): String {
        if (rate != null) {
            if (rate <= 0) {
                return "Free"
            } else {
                return getAmount(rate)
            }
        } else {
            return ""
        }
    }

    fun getAgeRequirement(age: Int?): String {
        if (age != null) {
            return " $age+";
        } else {
            return ""
        }
    }

    fun getDistanceVenueName(distance: Double?, venueName: String?): String {
        return "$distance mi - $venueName";
    }

    fun getAttending(attendingCount: Int?, notAttendingCount: Int?, maybeCount: Int?): String {
        return " Yes($attendingCount) No($notAttendingCount) May be($maybeCount)"
    }

    fun getEventModes(eventMode: Int?): String {
        if (eventMode == 0) {
            return Constant.PUBLIC
        } else if (eventMode == 1) {
            return Constant.PRIVATE
        }
        return " $eventMode"
    }

    fun getEventStatuses(eventStatus: Int?): String {
        if (eventStatus == 0) {
            return Constant.INACTIVE
        } else if (eventStatus == 1) {
            return Constant.ACTIVE
        } else if (eventStatus == 2) {
            return Constant.REJECTED
        }
        return " $eventStatus"
    }

    fun getTotalAvailableTickets(tickets: Int?): String {
        return " $tickets"
    }


    fun formatStringCreditCard(s: String): String {
        // Remove spacing char
        /* if (s.length > 0 && (s.length % 5) == 0) {

             val c = s.get(s.length - 1)
             if (space == c) {
                 s.delete(s.length - 1, s.length)
             }
         }
         // Insert char where needed.
         if (s.length > 0 && (s.length % 5) == 0) {
             val c = s.get(s.length - 1);
             // Only if its a digit where there should be a space we insert a space
             if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3) {
                 s.insert(s.length - 1, space.toString())
             }
         }*/

        val result = StringBuilder()
        for (i in 0 until s.length) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(s.get(i))
        }

        return result.toString()
    }

    fun sendEmail(recipient: String, mContext: Context) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SENDTO)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:$recipient")
//        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/

//        mIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))


        try {
            //start email intent
            (mContext as Activity).startActivity(
                Intent.createChooser(
                    mIntent,
                    "Choose Email Client..."
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun sendCrashReport(stackStrace: String, mContext: Activity) {
        if (Debug.DEBUG_EXCEPTION) {
            /*ACTION_SEND action to launch an email client installed on your Android device.*/
            val mIntent = Intent(Intent.ACTION_SEND)
            /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
            mIntent.data = Uri.parse("mailto:bhavesh4349@gmail.com")
            mIntent.type = "text/plain"
            // put recipient email in intent
            /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/

            mIntent.putExtra(Intent.EXTRA_SUBJECT, "Crash Report");
            mIntent.putExtra(
                Intent.EXTRA_TEXT, stackStrace
                        + "\n\n" + getDeviceInfo(mContext)
            );
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("bhavesh4349@gmail.com"))


            try {
                //start email intent
                mContext.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDeviceScreenResolution(mContext: Activity): String? {
        val display: Display = mContext.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x //device width
        val height: Int = size.y //device height
        return "$width x $height" //example "480 * 800"
    }

    fun getDeviceInfo(mContext: Activity): String {
        var str = ""
        str = Build.PRODUCT + "\n" +
                Build.MODEL + "\n" +
                Build.MANUFACTURER + "\n" +
                "Android " + Build.VERSION.RELEASE + "\n" +
                "API Level " + Build.VERSION.SDK_INT + "\n" +
                "OS " + System.getProperty("os.version") + "\n" +
                getDeviceScreenResolution(mContext = mContext)

        return str
    }

    fun fromHtml(source: String): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    fun isDateBefore(date: String): Boolean {
        val expDate = parseTime(date, "yyyy-MM")
        val currDateStr = parseTime(Date().time, "yyyy-MM")
        val todayDate = parseTime(currDateStr, "yyyy-MM")
        return expDate.compareTo(todayDate) < 0
    }

    fun formatNo(inputNum: String): String? {
        var inputNum = inputNum
        var suffix = ""
        var strings = inputNum.split(".")
        if (strings.size > 1) {
            inputNum = strings[0]
            suffix = "." + strings[1]
        }


        val regex = "(\\d)(?=(\\d{3})+$)"
        val splittedNum = inputNum.split("\\.").toTypedArray()
        return if (splittedNum.size == 2) {
            splittedNum[0].replace(regex.toRegex(), "$1,") + "." + splittedNum[1] + suffix
        } else {
            inputNum.replace(regex.toRegex(), "$1,") + suffix
        }


    }

    fun getUriForShare(context: Context, file: File): Uri? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
            } else {
                Uri.fromFile(file)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun loadLocalImage(img: ImageView, url: File, context: Context, resId: Int) {
        run {
            val options: RequestOptions = RequestOptions()
                .placeholder(resId)
                .error(resId);
            Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(options).into(img)
        }
    }
}
