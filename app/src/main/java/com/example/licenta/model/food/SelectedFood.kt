package com.example.licenta.model.food

import com.example.licenta.util.Date
import com.google.firebase.firestore.DocumentId
import java.time.LocalDate

data class SelectedFood(
    @DocumentId
    val id: String = "",
    val foodId: String = "",
    val userId: String = "",
    val quantity: Double = 0.0,
    val unit: FoodMeasureUnitEnum = FoodMeasureUnitEnum.GRAM,
    val dateSelected: String = Date.setCurrentDay()
) {
    companion object {
        const val ID = "id"
        const val FOOD_ID = "foodId"
        const val USER_ID = "userId"
        const val QUANTITY = "quantity"
        const val UNIT = "unit"
        const val DATE_SELECTED = "dateSelected"
    }
}
