package com.example.licenta.model

data class WeightExercise(
    override val name: String,
    override val group: String,
    val sets: Int,
    val reps: Int,
    val weight: Double
) : Exercise
