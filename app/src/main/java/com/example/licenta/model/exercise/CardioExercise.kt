package com.example.licenta.model.exercise

import com.google.firebase.firestore.DocumentId

data class CardioExercise(
    @DocumentId
    override val id: String,
    override val name: String,
    override val group: String,
    val distance: Double,
    val time: Long
) : Exercise
