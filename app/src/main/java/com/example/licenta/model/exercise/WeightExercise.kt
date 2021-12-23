package com.example.licenta.model.exercise

import com.google.firebase.firestore.DocumentId

data class WeightExercise(
    @DocumentId
    override val id: String,
    override val name: String,
    override val group: String,
    val sets: Int,
    val reps: Int,
    val weight: Double
) : Exercise
