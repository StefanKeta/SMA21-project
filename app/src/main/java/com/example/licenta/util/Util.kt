package com.example.licenta.util

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object Util {
    fun hideKeyboard(activity: Activity){
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText)
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
    }
}