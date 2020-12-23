package com.newproject.validator

import java.util.regex.Pattern


class NameValidator(override var value: String?, var prefix: String?) : Validatable {

    override var msg: String? = null

    override fun isValid(): Boolean {

        val pattern = Pattern.compile(
                "^[A-Za-z\\s\']*$"
        )
        if (value == null) {
            msg = "$prefix is required."
            return false
        } else if (value!!.isEmpty()) {
            msg = "$prefix is required."
            return false
        } else if (value!!.length < 3) {
            msg = "Min. 3 characters required in $prefix."
            return false
        } else if (!pattern.matcher(value).matches()) {
            msg = "Invalid format of $prefix. Valid format is : John Deo"
            return false
        }
        return true
    }

}