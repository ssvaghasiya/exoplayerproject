package com.newproject.ui.taskdetail.taskdetailstep2.viewmodel

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityTaskDetailStep2Binding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.home.view.HomeActivity
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDetails
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.AllProductDataModel
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.Product
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.TaskStep2DataModel
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.TaskStep2Details
import kotlinx.android.synthetic.main.alert_success_layout.view.*
import kotlinx.android.synthetic.main.corporative.view.*
import kotlinx.android.synthetic.main.lunch_break_dialogue_layout.view.*
import kotlinx.android.synthetic.main.resistent.view.*
import kotlin.collections.ArrayList


class TaskDetailsStep2ViewModel(application: Application) : BaseViewModel(application),
    AdapterView.OnItemSelectedListener {

    private lateinit var binder: ActivityTaskDetailStep2Binding
    private lateinit var mContext: Context

    lateinit var taskStep2DataModel: TaskStep2DataModel
    lateinit var allProductDataModel: AllProductDataModel
    var visitData: TaskDetails.Visit? = null
    var sample: Int? = 0
    var topicOfVisit: String? = null
    var doctorResponse: String? = null
    var potentialPharm: String? = null
    var reason: String? = null
    var product: List<Product.ProductData>? = null
    lateinit var productName: ArrayList<String>
    var date: String? = null
    lateinit var dialogView: View
    var timer: CountDownTimer? = null
    var alertDialog: AlertDialog? = null

    fun setBinder(binder: ActivityTaskDetailStep2Binding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        this.binder.topbarback.topBarClickListener = SlideMenuClickListener()
        binder.topbarback.isSettingShow = true
        binder.topbarback.isCenterTextShow = true
        binder.topbarback.txtCenterText.text = mContext.getString(R.string.task_details)
        binder.topbarback.switchStartVisit.isEnabled = false
        taskStep2DataModel = TaskStep2DataModel()
        allProductDataModel = AllProductDataModel()
        productName = ArrayList<String>()
        init()
    }

    private fun init() {
        var adapter = ArrayAdapter.createFromResource(
            mContext,
            R.array.items_spinner_step2,
            R.layout.style_spinner_layout
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binder.spinnerstep2drresponse.adapter = adapter
        binder.spinnerstep2drresponse.onItemSelectedListener = this
        selectPosition(0)
        getAllProducts()

        var btnAddViewMadeOrder = (mContext as Activity).findViewById<Button>(R.id.btnAddProduct)
        btnAddViewMadeOrder.setOnClickListener {
            try {
                var llView: View = (mContext as Activity).layoutInflater.inflate(
                    R.layout.item_product,
                    null,
                    false
                )
                var imgRemove = llView.findViewById<AppCompatImageView>(R.id.imgRemove)
                imgRemove.setOnClickListener {
                    binder.madeOrder.llTaskDetails.removeView(llView)
                }
                var tvMadeOrderProduct = llView.findViewById<TextView>(R.id.tvMadeOrderProduct)
                var llMadeOrderProduct = llView.findViewById<LinearLayout>(R.id.llMadeOrderProduct)
                llMadeOrderProduct.setOnClickListener {
                    val menu = PopupMenu(mContext, llMadeOrderProduct)

                    menu.setOnMenuItemClickListener {
                        //                            Toast.makeText(
                        //                                this@MainActivity,
                        //                                "You Clicked : " + item.getTitle(),
                        //                                Toast.LENGTH_SHORT
                        //                            ).show()

                        tvMadeOrderProduct.tag = it.itemId
                        tvMadeOrderProduct.text = it.title

                        true
                    }


//                    popup.getMenu().add(groupId, itemId, order, title);

                    product!!.forEachIndexed { index, productData ->
                        menu.menu.add(1, productData.productId, index, productData.name)
                    }

                    menu.show()
                }
                binder.madeOrder.llTaskDetails.addView(llView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        var btnAddViewCorporative = (mContext as Activity).findViewById<Button>(R.id.btnCorporative)
        btnAddViewCorporative.setOnClickListener {
            try {
                var llView: View = (mContext as Activity).layoutInflater.inflate(
                    R.layout.item_product_corporative,
                    null,
                    false
                )
                var tvCorporativeProduct = llView.findViewById<TextView>(R.id.tvCorporativeProduct)
                var llCorporativeProduct =
                    llView.findViewById<LinearLayout>(R.id.llCorporativeProduct)
                llCorporativeProduct.setOnClickListener {
                    val menu = PopupMenu(mContext, llCorporativeProduct)

                    menu.setOnMenuItemClickListener {
                        //                            Toast.makeText(
                        //                                this@MainActivity,
                        //                                "You Clicked : " + item.getTitle(),
                        //                                Toast.LENGTH_SHORT
                        //                            ).show()

                        tvCorporativeProduct.tag = it.itemId
                        tvCorporativeProduct.text = it.title

                        true
                    }


//                    popup.getMenu().add(groupId, itemId, order, title);

                    product!!.forEachIndexed { index, productData ->
                        menu.menu.add(1, productData.productId, index, productData.name)
                    }

                    menu.show()
                }
                binder.corporative.llCorporative.addView(llView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        doctorResponse = binder.spinnerstep2drresponse.selectedItem.toString()
        visitData =
            (mContext as Activity).intent.getSerializableExtra("step1data") as? TaskDetails.Visit?
        sample = (mContext as Activity).intent.getIntExtra("sample", 0)
        topicOfVisit = (mContext as Activity).intent.getStringExtra("topic")
        date = (mContext as Activity).intent.getStringExtra("date")

    }

    fun getAllProducts() {
        allProductDataModel.getAllProducts(mContext).observeForever { product ->
            dismissDialog()
            onCallResult(product)
        }
    }

    private fun onCallResult(product: Product?) = try {
        if (product != null) {
            when (product.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    this.product = product.data?.products
                    getProductName()
                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    showToast(product.data?.message)
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun getProductName() {
        for (i in 0 until product?.size!!) {
            productName.add(product!!.get(i).name.toString())
        }
    }


    private fun updateVisit() {
        var pharmEdit = (mContext as Activity).findViewById<EditText>(R.id.potentialPharmacies)
        var reasonEdit = (mContext as Activity).findViewById<EditText>(R.id.reasonComment)
        reason = reasonEdit.text.toString()
        potentialPharm = pharmEdit?.text.toString()
        taskStep2DataModel.visitId = visitData?.visitId.toString()
//        taskStep2DataModel.sample = sample.toString()
//        taskStep2DataModel.quantity = ""
        taskStep2DataModel.doctor_response = doctorResponse
        taskStep2DataModel.potential_pharmacy = potentialPharm
        taskStep2DataModel.potential_product = "1"
        taskStep2DataModel.reason =
            if (binder.indifferentOrResistent.visibility == View.VISIBLE) binder.indifferentOrResistent.spinnerReason.selectedItem.toString() else null
//        taskStep2DataModel.order_product = "1"
//        taskStep2DataModel.order_quantity = "2"
        taskStep2DataModel.product_id.clear()
        taskStep2DataModel.product_quantity.clear()

//        taskStep2DataModel.sample_product_id.clear()
//        taskStep2DataModel.sample_product_quantity.clear()

        if (binder.madeOrder.root.visibility == View.VISIBLE) {
//            binder.madeOrder.llTaskDetails.forEachIndexed { index, view ->
//                val tvMadeOrderProduct = view.findViewById<TextView>(R.id.tvMadeOrderProduct)
//                val quantity = view.findViewById<EditText>(R.id.txtQuantity)
//
////                if (taskStep2DataModel.sample.equals("1", true)) {
////
////                    taskStep2DataModel.sample_product_id.add(tvMadeOrderProduct.tag.toString())
////                    taskStep2DataModel.sample_product_quantity.add(quantity.text.toString())
////                } else {
//                taskStep2DataModel.product_id.add(tvMadeOrderProduct.tag.toString())
//                taskStep2DataModel.product_quantity.add(quantity.text.toString())
////                }
//
//
//                Debug.e(
//                    "madeorderprocess",
//                    "" + tvMadeOrderProduct.text + " " + tvMadeOrderProduct.tag + " " + quantity.text
//                )
//            }
        } else if (binder.corporative.visibility == View.VISIBLE) {

//            binder.corporative.llCorporative.forEachIndexed { index, view ->
//                val tvCorporativeProduct = view.findViewById<TextView>(R.id.tvCorporativeProduct)
//
////                if (taskStep2DataModel.sample.equals("1", true)) {
//
//                taskStep2DataModel.product_id.add(tvCorporativeProduct.tag.toString())
//
////                } else {
////                    taskStep2DataModel.product_id.add(tvCorporativeProduct.tag.toString())
////                }
//
//
//                Debug.e(
//                    "corporativeprocess",
//                    "" + tvCorporativeProduct.text + " " + tvCorporativeProduct.tag
//                )
//            }
        }
        showDialog("", mContext as Activity)
        taskStep2DataModel.updateTaskStep2(mContext).observeForever { taskStep2Details ->
            dismissDialog()
            onCallResult(taskStep2Details)
        }
    }

    private fun onCallResult(taskStep2Details: TaskStep2Details?) = try {
        if (taskStep2Details != null) {
            when (taskStep2Details.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    showToast("Success")
                    showCustomDialog()
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

    inner class ViewClickHandler {

        fun onPreviousClick(view: View) {
            try {
                finishActivity(mContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onCompleteProcessClick(view: View) {
            try {
                updateVisit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        doctorResponse = binder.spinnerstep2drresponse.selectedItem.toString()
        selectPosition(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun selectPosition(position: Int) {
        if (position == 0) {
            binder.corporative.visibility = View.VISIBLE
            binder.madeOrder.root.visibility = View.GONE
            binder.indifferentOrResistent.visibility = View.GONE

        } else if (position == 1) {
            binder.corporative.visibility = View.GONE
            binder.madeOrder.root.visibility = View.VISIBLE
            binder.indifferentOrResistent.visibility = View.GONE

        } else if (position == 2 || position == 3) {
            binder.corporative.visibility = View.GONE
            binder.madeOrder.root.visibility = View.GONE
            binder.indifferentOrResistent.visibility = View.VISIBLE
            var adapterReason = ArrayAdapter.createFromResource(
                mContext,
                R.array.items_spinner_reason,
                R.layout.style_spinner_layout
            )
            adapterReason.setDropDownViewResource(R.layout.spinner_dropdown_layout)
            binder.indifferentOrResistent.spinnerReason.adapter = adapterReason

        }
    }

    private fun showCustomDialog() {

        dialogView =
            LayoutInflater.from(mContext).inflate(R.layout.alert_success_layout, null, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setView(dialogView)
        alertDialog = builder.create()
        dialogView.txtSetTimer

        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        startTimer(4000)
    }

    private fun startTimer(millis: Long) {
        cancelTimer()
        timer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val doneMillis = millisUntilFinished

                val hour = ((doneMillis / 1000) / 3600).toString().padStart(2, '0')
                val min = ((doneMillis / 1000 / 60) % 60).toString().padStart(2, '0')
                val sec = ((doneMillis / 1000) % 60).toString().padStart(2, '0')
                dialogView.txtSetTimer.text = min + ":" + sec
            }

            override fun onFinish() {
                Debug.e("onTimer ", "finished")
                alertDialog?.dismiss()
                var intent = Intent(mContext, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                mContext.startActivity(intent)
            }
        }
        timer?.start()
    }

    private fun cancelTimer() {
        try {
            if (timer != null) {
                timer?.cancel()
                timer == null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}