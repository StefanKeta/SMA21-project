package com.example.licenta.firebase.db

import com.example.licenta.model.exercise.WeightExerciseRecord
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.RuntimeException

object WeightExerciseRecordDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun saveRecord(record: WeightExerciseRecord) {
        db
            .collection(CollectionsName.WEIGHT_EXERCISE_RECORD)
            .add(record)
            .addOnCompleteListener { ref ->
                if (!ref.isSuccessful) {
                    throw RuntimeException("Oops!Something went wrong")
                }
            }
    }
}