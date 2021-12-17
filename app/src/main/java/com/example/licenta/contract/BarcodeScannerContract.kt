package com.example.licenta.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.example.licenta.activity.camera.ScanBarcodeActivity
class BarcodeScannerContract : ActivityResultContract<Unit, Bundle?>() {
    override fun createIntent(context: Context, input:Unit): Intent {
        return Intent(context, ScanBarcodeActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        if(resultCode == Activity.RESULT_OK && intent != null){
            if(intent.extras != null)
                return intent.extras
        }
        return null
    }
}