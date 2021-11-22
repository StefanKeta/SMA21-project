package com.example.licenta.model.user


data class Goals(
    val goalsID: String,
    val userID: String,
    val userWeightGoal: Int,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
){
    companion object{
        const val GOALS_ID = "goalsID"
        const val USER_ID = "userID"
        const val WEIGHT_GOAL = "userWeightGoal"
        const val CALORIES = "calories"
        const val PROTEIN = "protein"
        const val CARBS = "carbs"
        const val FAT = "fat"
    }
}