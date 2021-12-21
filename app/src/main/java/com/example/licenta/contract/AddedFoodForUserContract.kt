package com.example.licenta.contract

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.licenta.activity.diary.AddFoodActivity
import com.example.licenta.model.food.SelectedFood
import com.example.licenta.util.IntentConstants

class AddedFoodForUserContract:ActivityResultContract<String,Boolean>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, AddFoodActivity::class.java)
        intent.putExtra(SelectedFood.DATE_SELECTED,input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if(resultCode == RESULT_OK){
            if(intent != null){
                return intent.getBooleanExtra(IntentConstants.IS_SELECTED_FOOD_SAVED,false)
            }
        }
        return false
    }
}