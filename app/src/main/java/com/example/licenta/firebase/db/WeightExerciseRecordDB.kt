package com.example.licenta.firebase.db

import com.example.licenta.model.exercise.WeightExerciseRecord
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.RuntimeException

object WeightExerciseRecordDB {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun exerciseAdapterOptions(date: String): FirestoreRecyclerOptions<WeightExerciseRecord> {
        val query = db
                .collection(CollectionsName.WEIGHT_EXERCISE_RECORDS)
                .whereEqualTo(WeightExerciseRecord.DATE, date)

        return FirestoreRecyclerOptions
            .Builder<WeightExerciseRecord>()
            .setQuery(query, WeightExerciseRecord::class.java)
            .build()
    }

    fun saveRecord(record: WeightExerciseRecord,callback: (Boolean) -> Unit) {
        db
            .collection(CollectionsName.WEIGHT_EXERCISE_RECORDS)
            .add(record)
            .addOnCompleteListener { ref ->
                if (ref.isSuccessful){
                    callback(true)
                } else{
                    callback(false)
                    throw RuntimeException("Oops!Something went wrong")
                }
            }
    }
}