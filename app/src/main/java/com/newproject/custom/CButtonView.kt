package com.newproject.custom

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import com.newproject.apputils.Utils


class CButtonView : AppCompatButton {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    fun init() {
        if (!isInEditMode) {
            try {
                typeface = Utils.getRegular(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}