package com.newproject.validator

import java.util.regex.Pattern


class CodeValidator(override var value : String?) : Validatable {

    override var msg: String? = null

    override fun isValid(): Boolean {
        val CODE_PATTERN = Pattern.compile(
                "[0-9]{4}$"
        )
        if(value == null){
            msg = "Code is required."
            return false
        }else if(!CODE_PATTERN.matcher(value).matches()){
            msg = "Invalid format. Valid format is : 9034"
            return false
        }
        return true
    }

}