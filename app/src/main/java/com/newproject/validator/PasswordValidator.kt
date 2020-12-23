package com.newproject.validator

import android.content.Context
import com.newproject.R
import java.util.regex.Pattern

class PasswordValidator(override var value: String?, var prefix: String?, var context: Context?) : Validatable {
    override var msg: String? = null

    constructor(context: Context?, value: String?) : this(value, "", null) {
        this.value = value
        this.context = context
    }

    override fun isValid(): Boolean {
        val passwordPatten = Pattern.compile(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{7,20}")
        if (isEmpty().not()) {
            return false
        } else if (value!!.length < 7) {
            msg = context?.getString(R.string.err_pass_min)
            return false
        }  else if (!passwordPatten.matcher(value!!).matches()) {
            msg = context?.getString(R.string.err_pass)
            return false
        }
        return true
    }

    private fun isEmpty(): Boolean {
        if (value == null) {
            msg = "$prefix Password is required."
            return false
        } else if (value!!.isEmpty()) {
            msg = "$prefix Password is required."
            return false
        }
        return true
    }

    fun isValidPassword(): Boolean {
        if (isEmpty().not()) {
            return false
        } else if (value!!.length < 7 || value!!.length > 20) {
            msg = "Invalid password"
            return false
        }
        return true
    }


    fun isValidConfirmPassword(cPassword: CharSequence?): Boolean {
        msg = "New password and Repeat password should match."
        return (value.toString() == cPassword.toString())
    }

    fun isOldPasswordMatchedNewPassword(oldPassword: CharSequence?): Boolean {
        msg = "Old password and new password should not be same."
        return (value.toString() == oldPassword.toString())
    }
}