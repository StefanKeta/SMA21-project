package com.example.licenta.util

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object Util {
    const val MINIMUM_HEIGHT_CM = 54
    const val MAXIMUM_HEIGHT_CM = 250
    const val MINIMUM_WEIGHT_KG = 25

    fun convertFeetInchesToCm(feet : Int, inches : Int = 0) : Double{
        return (feet.toFloat()*30.48f + inches.toFloat()*2.54f).toDouble()
    }

    fun convertLbsToKg(lbs:Int):Double{
        return (lbs.toDouble()/2.2046)
    }

    fun hideKeyboard(activity: Activity){
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText)
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
    }
}