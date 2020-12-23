package com.newproject.apputils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.newproject.R
import com.newproject.databinding.DialogExceptionDialogBinding

class ErrorAlertDialog {

    private var isYesShow: Boolean = false
    private var isNoShow: Boolean = false

    private var positiveButtonLabel: String? = null
    private var negativeButtonLabel: String? = null

    private var context: Context

//    var alertDialog: AlertDialog? = null

    private lateinit var title: String
    private lateinit var message: String

    lateinit var dialogButtonClick: DialogButtonClick

    constructor(context: Context) {
        this.context = context
    }

    fun setTitle(title: String): ErrorAlertDialog {
        this.title = title
        return this
    }

    fun setMessage(message: String): ErrorAlertDialog {
        this.message = message
        return this
    }

    fun setPositiveButton(positiveButtonLabel: String): ErrorAlertDialog {
        isYesShow = true
        this.positiveButtonLabel = positiveButtonLabel
        return this
    }

    fun setNegativeButton(negativeButtonLabel: String): ErrorAlertDialog {
        isNoShow = true
        this.negativeButtonLabel = negativeButtonLabel
        return this
    }

    fun setOnButtonClickListener(dialogButtonClick: DialogButtonClick): ErrorAlertDialog {
        this.dialogButtonClick = dialogButtonClick
        return this
    }

    fun show() {

        try {
            var pd: AlertDialog.Builder = AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
            val binding = DataBindingUtil.inflate<DialogExceptionDialogBinding>(LayoutInflater.from(context),
                    R.layout.dialog_exception_dialog, null, false)
            binding.tvTitle.text = title
            binding.tvMessage.text = message
            if (isNoShow) {
                binding.tvNo.text = negativeButtonLabel
                binding.tvNo.visibility = View.VISIBLE
            } else {
                binding.tvNo.visibility = View.GONE
            }
            if (isYesShow) {
                binding.tvYes.text = positiveButtonLabel
                binding.tvYes.visibility = View.VISIBLE
            } else {
                binding.tvYes.visibility = View.GONE
            }
            binding.root.findViewById<LinearLayout>(R.id.llNo).setOnClickListener {
//                alertDialog!!.dismiss()
                dialogButtonClick.onNegativeClick()
            }

            binding.root.findViewById<LinearLayout>(R.id.llYes).setOnClickListener {
//                alertDialog!!.dismiss()
                dialogButtonClick.onPositiveClick()
            }
            pd.setView(binding.root)
            pd.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface DialogButtonClick {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}