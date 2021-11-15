package com.example.licenta.util

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Util {
    const val MINIMUM_HEIGHT_CM = 54
    const val MAXIMUM_HEIGHT_CM = 250
    const val MINIMUM_WEIGHT_KG = 25

    fun convertFeetInchesToCm(feet: Int, inches: Int = 0): Int {
        val height = (feet * 30.48 + inches * 2.54)
        return if (height - height.toInt().toDouble() < 0.50) height.toInt() else height.toInt() + 1
    }

    fun convertCmToFeetInches(cm: Int): Pair<Int, Int> {
        val feet = cm / 30.48
        val feetRemainder = feet - feet.toInt()
        val newFeet = if (feet - feet.toInt().toDouble() > 50) feet.toInt() + 1 else feet.toInt()
        val inches = feetRemainder * 30.48 / 2.54
        val newInches =
            if (inches - inches.toInt().toDouble() > 0) inches.toInt() + 1 else inches.toInt()
        return Pair(newFeet, newInches)
    }

    fun convertLbsToKg(lbs: Int): Int {
        val weight = (lbs.toDouble() / 2.2046)
        return if(weight - weight.toInt()>0.50) weight.toInt() + 1 else weight.toInt()
    }

    fun convertKgToLbs(kg: Double): Int {
        val weight = (kg * 2.2046)
        return if (weight - weight.toInt() > 0.50) weight.toInt() + 1 else weight.toInt()
    }

    fun hideKeyboard(activity: Activity) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText)
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
    }

    fun getTimestampFromDate(date: String): Timestamp {
        return Timestamp(
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                .parse(date)
                .time
        )
    }

    fun getDateFromTimestamp(timestamp: Long): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            .format(Date(timestamp))
    }


}