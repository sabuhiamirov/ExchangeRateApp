package com.presentation.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.requestFocusAndOpenKeyboard() {
    this.postDelayed({
        this.requestFocus()
        val inputMethodManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }, 500)
}