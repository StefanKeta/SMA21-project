package com.example.licenta.model.exercise

import com.example.licenta.util.Date
import com.google.firebase.firestore.DocumentId

data class WeightExerciseRecord(
    @DocumentId
    val id: String,
    val exerciseId: String,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val date: String = Date.setCurrentDay()
) {
    companion object {
        const val ID = "id"
        const val EXERCISE_ID = "exerciseId"
        const val SETS = "sets"
        const val REPS = "reps"
        const val WEIGHT = "weight"
        const val DATE = "date"
    }
}
