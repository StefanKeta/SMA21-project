package com.example.licenta.model.food

import com.google.firebase.firestore.DocumentId

data class Food(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val barcode: String = "",
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
) {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val BARCODE = "barcode"
        const val CALORIES = "calories"
        const val PROTEIN = "protein"
        const val CARBS = "carbs"
        const val FAT = "fat"
    }
}
