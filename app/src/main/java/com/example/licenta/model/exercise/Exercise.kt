package com.example.licenta.model.exercise

import com.google.firebase.firestore.DocumentId

data class Exercise(
    @DocumentId
    val id: String = "",
    val group: String = "",
    val name: String = "",
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val GROUP = "group"
    }
}
