package com.example.licenta.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.licenta.R
import com.example.licenta.util.IntentConstants

class AddCustomExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_custom_exercise)
        val code = intent.getIntExtra(
            IntentConstants.EXERCISE_ADDING_TYPE,
            IntentConstants.ADD_CUSTOM_EXERCISE_CODE
        )
        if (code == IntentConstants.ADD_CUSTOM_EXERCISE_CODE)
        //display add exercise layout
        else {
            //display add group layout
        }
    }
}