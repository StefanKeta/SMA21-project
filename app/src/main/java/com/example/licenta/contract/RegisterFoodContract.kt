package com.example.licenta.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.example.licenta.activity.camera.ScanBarcodeActivity
import com.example.licenta.activity.diary.RegisterFoodToDbActivity
import com.example.licenta.model.food.Food
import com.example.licenta.util.IntentConstants

class RegisterFoodContract : ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, RegisterFoodToDbActivity::class.java)
        intent.putExtra(Food.BARCODE, input)
        return Intent(context, ScanBarcodeActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getBooleanExtra(IntentConstants.IS_FOOD_SAVED, false)
        }
        return false
    }
}