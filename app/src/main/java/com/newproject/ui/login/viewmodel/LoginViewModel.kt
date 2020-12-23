package com.newproject.ui.login.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.RequestParamsUtils
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityLoginBinding
import com.newproject.ui.home.view.HomeActivity
import com.newproject.ui.login.datamodel.LoginData
import com.newproject.ui.login.datamodel.LoginDataModel
import com.newproject.validator.EmailValidator
import com.google.gson.Gson

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityLoginBinding
    private lateinit var mContext: Context
    lateinit var loginDataModel: LoginDataModel


    fun setBinder(binder: ActivityLoginBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        loginDataModel = LoginDataModel()

        init()
    }

    private fun init() {
        if (Debug.DEBUG) {
            binder.edtUserEmail.setText("rep@crm.com")
            binder.edtPassword.setText("password")
        }
    }

    private fun doLogin(view: View) {

        loginDataModel.email = binder.edtUserEmail.text.toString()
        loginDataModel.password = binder.edtPassword.text.toString()
        showDialog("", mContext as Activity)
        loginDataModel.login(mContext).observeForever { loginData ->
            dismissDialog()
            onCallResult(loginData)
        }
    }

    private fun onCallResult(loginData: LoginData?) = try {
        if (loginData != null) {
            when (loginData.statusCode) {
                Constant.RESPONSE_SUCCESS_CODE -> {
                    Utils.setPref(
                        mContext,
                        RequestParamsUtils.AUTHENTICATIONTOKEN,
                        loginData.data?.token!!
                    )
                    Utils.setPref(
                        mContext,
                        Constant.LOGIN_INFO,
                        Gson().toJson(loginData)
                    )
                    Utils.setPref(
                        mContext,
                        Constant.EMPLOYEE_ID,
                        loginData.data!!.employee?.id!!
                    )


                    /*
                    * Moving to 'HomeActivity' */
                    val i = Intent((mContext as Activity), HomeActivity::class.java)
                    i.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    (mContext as Activity).startActivity(i)
                    finishActivity(mContext)

                }
                Constant.RESPONSE_FAILURE_CODE -> {
                    try {
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    if (loginData.errors?.email.isNullOrEmpty().not()) {
                        showToast(loginData.errors?.email)
                    } else {
                        showToast(loginData.message)
                    }
                }
            }


        } else {

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    inner class ViewClickHandler {

        fun onSignInClick(view: View) {
            try {
                if (isValidate()) {
                    doLogin(view)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isValidate(): Boolean {
        val emailValidator = EmailValidator(binder.edtUserEmail.text.toString())
        if (!emailValidator.isValid()) {
            showToast(emailValidator.msg)
            return false
        } else if (binder.edtPassword.text.isNullOrEmpty()) {
            showToast(mContext.getString(R.string.password_field_is_require))
            return false
        }

        return true
    }
}
