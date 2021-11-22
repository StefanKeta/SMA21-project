package com.example.licenta.math

import com.example.licenta.util.PersonalWeightPreference

object MacroCalculator {
    fun calculateMacros(
        preference: PersonalWeightPreference,
        calories: Int
    ): Triple<Int, Int, Int> {

        return when (preference) {
            PersonalWeightPreference.FAT_LOSS -> {
                val proteinCalories = (calories.toDouble() * 0.40)
                val fatCalories = (calories.toDouble() * 0.30)
                val carbsCalories = calories - proteinCalories - fatCalories

                val proteinGrams = (proteinCalories / 4.00).toInt()
                val fatGrams = (fatCalories / 9.00).toInt()
                val carbsGrams = (carbsCalories / 4.00).toInt()
                Triple(proteinGrams, fatGrams, carbsGrams)
            }
            PersonalWeightPreference.MAINTAIN -> {
                val proteinCalories = (calories.toDouble() * 0.25)
                val fatCalories = (calories.toDouble() * 0.25)
                val carbsCalories = calories - proteinCalories - fatCalories

                val proteinGrams = (proteinCalories / 4.00).toInt()
                val fatGrams = (fatCalories / 9.00).toInt()
                val carbsGrams = (carbsCalories / 4.00).toInt()
                Triple(proteinGrams, fatGrams, carbsGrams)
            }
            else -> {
                val proteinCalories = (calories.toDouble() * 0.30)
                val fatCalories = (calories.toDouble() * 0.30)
                val carbsCalories = calories - proteinCalories - fatCalories

                val proteinGrams = (proteinCalories / 4.00).toInt()
                val fatGrams = (fatCalories / 9.00).toInt()
                val carbsGrams = (carbsCalories / 4.00).toInt()
                Triple(proteinGrams, fatGrams, carbsGrams)
            }
        }
    }

}