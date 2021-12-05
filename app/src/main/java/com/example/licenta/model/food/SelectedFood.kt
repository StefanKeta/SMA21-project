package com.example.licenta.model.food

import java.time.OffsetDateTime

data class SelectedFood(
    val foodID : String = "",
    val quantity:Int = 0,
    val unit: MeasureUnitEnum = MeasureUnitEnum.GRAM,
    val dateSelected:Long = OffsetDateTime.now().toEpochSecond()
)
