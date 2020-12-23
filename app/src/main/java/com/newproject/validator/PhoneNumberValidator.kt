package com.newproject.validator

import java.util.regex.Pattern

class PhoneNumberValidator(override var value: String?) : Validatable {

    override var msg: String? = null
    var isOptional = false;

    constructor(value: String?, isOptional: Boolean) : this(value) {
        this.value = value
        this.isOptional = isOptional

    }

    override fun isValid(): Boolean {
        val pattern = Pattern.compile(
                "^[0]{1}[7,8,9]{1}[0-9]{9}$"
        )

        val pattern1 = Pattern.compile(
                "^[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}$"
        )

        if (value == null) {
            msg = "Phone Number is required."
            return isOptional
        } else if (value!!.isEmpty()) {
            msg = "Phone Number is required."
            return isOptional
        } else if (value!!.length < 8) {
            msg = "Min. 8 numbers required in Phone Number."
            return isOptional
        } else if (!pattern.matcher(value).matches()) {
            msg = "Phone number should be in 0801234567 format"
            return false
        }
        return true
    }

}