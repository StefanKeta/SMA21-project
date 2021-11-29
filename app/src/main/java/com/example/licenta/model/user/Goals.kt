package com.example.licenta.model.user

import com.google.firebase.firestore.DocumentId


data class Goals(
    @DocumentId
    val goalsID: String = "",
    val userID: String = "",
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
) {
    companion object {
        const val GOALS_ID = "goalsID"
        const val USER_ID = "userID"
        const val CALORIES = "calories"
        const val PROTEIN = "protein"
        const val CARBS = "carbs"
        const val FAT = "fat"
    }
}