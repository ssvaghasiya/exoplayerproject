package com.newproject.ui.createnewtask.viewmodel

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.UriHelper
import com.newproject.apputils.Utils
import com.newproject.base.view.BaseActivity
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityCreateNewTaskBinding
import com.newproject.databinding.DialogPicChooserBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.clients.datamodel.Client
import com.newproject.ui.createnewtask.datamodel.CreateTask
import com.newproject.ui.createnewtask.datamodel.CreateTaskDataModel
import com.mikepenz.materialdrawer.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CreateNewTaskViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityCreateNewTaskBinding
    private lateinit var mContext: Context
    lateinit var createTaskDataModel: CreateTaskDataModel
    var clientData: Client.Data.ClientData? = null
    var cal = Calendar.getInstance()

    fun setBinder(binder: ActivityCreateNewTaskBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbarback.topBarClickListener = SlideMenuClickListener()
        binder.topbarback.isSettingShow = true
        binder.topbarback.isCenterTextShow = true
        createTaskDataModel = CreateTaskDataModel()
        init()
    }

    private fun init() {
        clientData =
            (mContext as Activity).intent.getSerializableExtra("ClientDetail") as? Client.Data.ClientData
        binder.txtCompanyName.setText(clientData?.companyName)
        binder.txtPersonName.setText(clientData?.firstName)
        binder.txtArea.setText(clientData?.areaName)
        binder.txtCity.setText(clientData?.city)
        updateDateInView()
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


    var fileUri: File? = null
    val REQ_CAPTURE_IMAGE = 4470
    val REQ_PICK_IMAGE = 4569
    var type = ""

    fun showPictureChooser() {
        val pd: AlertDialog.Builder =
            AlertDialog.Builder((mContext as Activity), R.style.MyAlertDialogStyle)
        val binding = DataBindingUtil.inflate<DialogPicChooserBinding>(
            LayoutInflater.from(mContext),
            R.layout.dialog_pic_chooser, null, false
        )

        pd.setView(binding.root)
        val dialog = pd.create()

        binding.tvChooseGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            try {
                (mContext as Activity).startActivityForResult(
                    Intent.createChooser(
                        intent,
                        mContext.getString(R.string.err_select_image)
                    ), REQ_PICK_IMAGE
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            dialog.dismiss()
        }

        binding.tvChooseCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true)
            fileUri = File(Utils.getOutputMedia(mContext)!!.absolutePath)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Utils.getUriForShare(mContext, fileUri!!))
            try {
                (mContext as Activity).startActivityForResult(
                    Intent.createChooser(
                        intent,
                        mContext!!.getString(R.string.err_select_image)
                    ), REQ_CAPTURE_IMAGE
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            dialog.dismiss()
        }

        dialog.show()

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CAPTURE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    if (fileUri == null || !fileUri!!.exists()) {
                        val tmpFileUri = data!!.data
                        Debug.e("", "tmp_fileUri : " + tmpFileUri!!.path!!)
                        val selectedImagePath = UriHelper.getPath(
                            mContext as Activity, tmpFileUri
                        )
                        fileUri = File(selectedImagePath!!)

                    }
                    if (fileUri != null && fileUri!!.exists()) {
                        afterImageSelected(fileUri)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        } else if (requestCode == REQ_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                val tmpFileUri = data!!.data
                Debug.e("", "tmp_fileUri : " + tmpFileUri!!.path!!)
                val selectedImagePath = UriHelper.getPath(
                    mContext as Activity,
                    tmpFileUri
                )

                fileUri = File(selectedImagePath!!)
                if (fileUri != null && fileUri!!.exists()) {
                    afterImageSelected(fileUri)

                    Debug.e("", "fileUri : " + fileUri!!.absolutePath)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun afterImageSelected(fileUri: File?) {
        val image = File(fileUri!!.getPath())
        val cardName = image.name
        binder.txtUploadCard.text = cardName
        createTaskDataModel.card = image
    }

    fun addVisits() {
        prepareCall()
        showDialog("", mContext as Activity)
        createTaskDataModel.createTask(mContext)
            .observeForever { createTask ->
                dismissDialog()
                onCallResult(createTask)
            }
    }

    private fun onCallResult(createTask: CreateTask?) = try {
        if (createTask != null) {
            when (createTask.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    showToast(mContext.getString(R.string.task_created_successfully))
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                        showToast("Fail")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast("Fail")
                }
            }
        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun prepareCall() {
        var employeeId = Utils.getPref(mContext, Constant.EMPLOYEE_ID, 0)
        createTaskDataModel.company_id = clientData?.company_id.toString()
        createTaskDataModel.lead_person_id = clientData?.leadId.toString()
        createTaskDataModel.area_id = clientData?.area_id.toString()
        createTaskDataModel.employee_id = employeeId.toString()
        createTaskDataModel.city = clientData?.city.toString()
        createTaskDataModel.visit_topic = binder.txtTopicOfVisit.text.toString()
        updateDate()
        updateTime()
    }


    inner class ViewClickHandler {

        fun onCreateNewTask(view: View) {
            try {
                if (isValidate()) {
                    addVisits()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onCancle(view: View) {
            try {

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

        fun onCardSelect(view: View) {
            try {
                checkPermissionStorageAndCamera(mContext as BaseActivity, object : PermissionListener {
                    override fun onGranted() {
                        showPictureChooser()
                    }

                    override fun onDenied() {
                        showPermissionAlert()
                    }
                })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDateInView() {
        val myFormat = "MMM dd, yyyy '@' h:mm a" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        binder.txtDateTime.text = sdf.format(cal.time).toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDate() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        createTaskDataModel.visitDate = sdf.format(cal.time).toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateTime() {
        val myFormat = "h:mm:ss" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        createTaskDataModel.visitTime = sdf.format(cal.time).toString()
    }

    fun isValidate(): Boolean {
        if (createTaskDataModel.card == null) {
            showToast(mContext.getString(R.string.card_require))
            return false
        } else if (binder.txtTopicOfVisit.text.isNullOrEmpty()) {
            showToast(mContext.getString(R.string.visit_topic_require))
            return false
        }
        return true
    }


    fun showPermissionAlert() {
        var dialog: androidx.appcompat.app.AlertDialog? = null
        val builder = androidx.appcompat.app.AlertDialog.Builder(
            mContext as BaseActivity,
            R.style.MyAlertDialogStyle
        )
        builder.setTitle(mContext.getString(R.string.need_permission_title))
        builder.setCancelable(false)
        builder.setMessage(mContext.getString(R.string.err_need_permission_msg))
        builder.setPositiveButton(R.string.btn_ok) { dialog, which ->
            dialog.dismiss()
            mContext.startActivity(
                Intent(
                    android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
            )
        }
        builder.setNeutralButton(R.string.btn_cancel) { dialog, which ->
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog!!.show()

    }
}