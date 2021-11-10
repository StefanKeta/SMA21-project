package com.example.licenta.model

data class CardioExercise(
    override val name: String,
    override val group: String,
    val recordDistance: Double,
    val distance: Double,
    val time: Long
) : Exercise
