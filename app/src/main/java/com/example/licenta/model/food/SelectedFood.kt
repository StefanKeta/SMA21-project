package com.example.licenta.model.food

import com.example.licenta.util.Date

data class SelectedFood(
    val id : String = "",
    val foodId:String ="",
    val userId:String ="",
    val quantity:Double = 0.0,
    val unit: FoodMeasureUnitEnum = FoodMeasureUnitEnum.GRAM,
    val dateSelected:Long = Date.today()
){
    companion object{
        const val ID = "id"
        const val FOOD_ID = "food_id"
        const val USER_ID = "user_id"
        const val QUANTITY = "quantity"
        const val UNIT = "unit"
        const val DATE_SELECTED = "date_selected"
    }
}
