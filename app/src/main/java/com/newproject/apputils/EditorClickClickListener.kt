package com.newproject.apputils

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class EditorClickClickListener(button: AppCompatButton) : TextView.OnEditorActionListener{

    private var btnClick : AppCompatButton = button

    override fun onEditorAction(p0: TextView?,actionId: Int,p2: KeyEvent?): Boolean {
        if (actionId and EditorInfo.IME_MASK_ACTION == EditorInfo.IME_ACTION_DONE) {
            btnClick.performClick()
            return true
        }
        return false
    }

}